package com.example.forhealthfullapp

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.preference.PreferenceManager
import com.example.forhealthfullapp.bluetooth.BluetoothChatService
import com.example.forhealthfullapp.bluetooth.Constants
import com.example.forhealthfullapp.bluetooth.Devices
import kotlinx.android.synthetic.main.control_panal.*
import java.util.*

class Control_Panal : AppCompatActivity() {

    private var mConnectedDeviceName: String? = null
    private var mOutStringBuffer: StringBuffer? = null
    private var mInStringBuffer: StringBuffer? = null

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mChatService: BluetoothChatService? = null

    var address: String? = null
    var deviceName: String? = null

    var i =0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.control_panal)

        // hide bottom navigation bar

        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        val decorView = window.decorView
        decorView.systemUiVisibility = uiOptions



        val newint = intent
            deviceName = newint.getStringExtra(Devices.EXTRA_NAME)
            address = newint.getStringExtra(Devices.EXTRA_ADDRESS)


        backbtnp.setOnClickListener(View.OnClickListener {

            val ControlPIn = Intent(this@Control_Panal, Paireddeviceslist::class.java)

                    startActivity(ControlPIn)
                    finish()
        })


            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

            var port_number = 1
            if (!sharedPreferences.getBoolean("default_port", true)) {
                val port_value = sharedPreferences.getString("port", "0")
                port_number = port_value!!.toInt()
            }
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            mChatService = BluetoothChatService(this, mHandler)
            mOutStringBuffer = StringBuffer("")
            mInStringBuffer = StringBuffer("")

            val device = mBluetoothAdapter!!.getRemoteDevice(address)
            mChatService!!.connect(device, port_number, true)

        var msg = "0"

        playbtn.setOnClickListener(View.OnClickListener {
            msg = "1"
            msg.send()
        })

        pausebtn.setOnClickListener(View.OnClickListener {
            msg = "0"
            msg.send()
        })

        stopbtn.setOnClickListener(View.OnClickListener {
            msg = "2"
            msg.send()
        })


        stop.setOnClickListener(View.OnClickListener {
            disconnect()
        })



    }

    private fun setData(message: String) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show()

      if(message.toInt()==1){
            //          Play
      }else{
          if(message.toInt()==0){
              //          Stop
          }
      }

    }

    // Connect and chat

    fun String.send() {
        if (mChatService!!.state != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this@Control_Panal, "cant send message - not connected", Toast.LENGTH_SHORT).show()
            return
        }
        if (isNotEmpty()) {
            val send = toByteArray()
            mChatService!!.write(send)
            mOutStringBuffer!!.setLength(0)

        }
    }

    private fun disconnect() {
        if (mChatService != null) {
            mChatService!!.stop()
        }
        finish()
    }

    private fun msg(message: String) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun IncommingMsg(message: String) {

        setData(message)

    }

    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constants.MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                    BluetoothChatService.STATE_CONNECTED -> {
                        Log.d("status", "connected")
                        msg("Connected to $deviceName")
                        // send the protocol version to the server
                        """
                                                3,${Constants.PROTOCOL_VERSION},${Constants.CLIENT_NAME}
                                                
                                                """.trimIndent().send()
                    }
                    BluetoothChatService.STATE_CONNECTING -> {
                        Log.d("status", "connecting")
                        msg("Connecting to $deviceName")
                    }
                    BluetoothChatService.STATE_LISTEN, BluetoothChatService.STATE_NONE -> {
                        Log.d("status", "not connected")
                        msg("Not connected")
                        disconnect()
                    }
                }
                Constants.MESSAGE_WRITE -> {
                    val writeBuf = msg.obj as ByteArray
                    // construct a string from the buffer
                    val writeMessage = String(writeBuf)
                }
                Constants.MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    // construct a string from the valid bytes in the buffer
                    val readData = String(readBuf, 0, msg.arg1)
                    // message received
                    IncommingMsg("$readData")

                }
                Constants.MESSAGE_DEVICE_NAME -> {
                    // save the connected device's name
                    mConnectedDeviceName = msg.data.getString(Constants.DEVICE_NAME)
                    if (null != this) {
                        Toast.makeText(
                                applicationContext, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Constants.MESSAGE_TOAST -> if (null != this) {
                    Toast.makeText(
                            applicationContext, msg.data.getString(Constants.TOAST),
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        disconnect()
    }

}
