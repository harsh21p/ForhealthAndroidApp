package com.example.forhealthfullapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.*
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forhealthfullapp.bluetooth.Devices
import com.example.forhealthfullapp.bluetooth.ModelForPairedDevices
import com.example.forhealthfullapp.bluetooth.PairedDeviceHolderAdapter
import kotlinx.android.synthetic.main.paireddeviceslist.*
import java.util.*

class Paireddeviceslist : AppCompatActivity() {
    private var pdeviceList = ArrayList<ModelForPairedDevices>()
    private var ndeviceList = ArrayList<ModelForPairedDevices>()
    private val pdeviceadapter = PairedDeviceHolderAdapter(pdeviceList,this)
    private val ndeviceadapter = PairedDeviceHolderAdapter(ndeviceList,this)
    companion object{
        lateinit var mBtAdapter: BluetoothAdapter

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide Top notification bar

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.paireddeviceslist)

        // hide bottom navigation bar

        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        val decorView = window.decorView
        decorView.systemUiVisibility = uiOptions


        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.registerReceiver(mReceiver, filter)
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

        this.registerReceiver(mReceiver, filter)
        val pdevicesRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewofpaireddevices)
        val ndevicesRecyclerView = findViewById<RecyclerView>(R.id.newdevices)

        ndevicesRecyclerView.layoutManager = LinearLayoutManager(this)
        pdevicesRecyclerView.layoutManager = LinearLayoutManager(this)
        ndevicesRecyclerView.adapter = ndeviceadapter
        pdevicesRecyclerView.adapter = pdeviceadapter

        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices = mBtAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                var device = ModelForPairedDevices(device.name, device.address)
                pdeviceList.add(device)
                pdeviceadapter.notifyDataSetChanged()
            }
        } else {
            paired_devices.text="Not Found"
        }
    }

    private fun doDiscovery() {
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }
        mBtAdapter!!.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }
        unregisterReceiver(mReceiver)
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }
        unregisterReceiver(mReceiver)
    }

    override fun onPause() {
        super.onPause()
    }

    fun onItemClick(position: Int) {
        val m = pdeviceList[position]
        var address = m.getAddress().toString()
        var deviceName = m.getName().toString()
        val i = Intent(this, SplashScreen::class.java)

        i.putExtra(Devices.EXTRA_NAME, deviceName)
        i.putExtra(Devices.EXTRA_ADDRESS, address)
        startActivity(i)

    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {

                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if(device!=null){
                        var device1 = ModelForPairedDevices(device!!.name, device!!.address)
                        ndeviceList.add(device1)
                        ndeviceadapter.notifyDataSetChanged()
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Toast.makeText(applicationContext,"Started",Toast.LENGTH_LONG).show()
                    if(ndeviceList.isEmpty()) {
                        available_devices.text = "Searching..."
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Toast.makeText(applicationContext,"Finished",Toast.LENGTH_LONG).show()
                    if(ndeviceList.isEmpty()) {
                        available_devices.text = "Not Found"
                    }
                }
            }
        }
    }

}


