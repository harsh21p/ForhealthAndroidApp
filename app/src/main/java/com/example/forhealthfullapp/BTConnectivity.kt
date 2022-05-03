package com.example.forhealthfullapp

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.btconectivity.*

class BTConnectivity : AppCompatActivity() {
    var bAdapter = BluetoothAdapter.getDefaultAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.btconectivity)

        // hide bottom navigation bar

        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        val decorView = window.decorView
        decorView.systemUiVisibility = uiOptions


        val PBTIntent = Intent(this@BTConnectivity, Paireddeviceslist::class.java)

        btnextbutton.setOnClickListener(View.OnClickListener {

            if(bAdapter.isEnabled){

                startActivity(PBTIntent)
                finish()

            }
            else{
                Toast.makeText(this,"Please turn on bluetooth",Toast.LENGTH_LONG).show()
            }


        })




    }

    override fun onResume() {
        super.onResume()
        val timeout = 500
        Handler().postDelayed({
            checkBluetoothState()
        }, timeout.toLong())
    }

    private fun checkBluetoothState(){

        if(bAdapter.isEnabled){
            bluetoothswitch.isChecked = true
            bluetoothonoffmsg.text="Bluetooth is on"
            bletoothicon.setImageResource(R.drawable.ic_baseline_bluetooth_24)
//            btnextbutton.isEnabled=true
        }else {
            bluetoothswitch.isChecked = false
            bluetoothonoffmsg.text="Bluetooth is off"
            bletoothicon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
//            btnextbutton.isEnabled=false
        }
        bluetoothswitch.setOnClickListener(View.OnClickListener {
            if (bAdapter.isEnabled){
                bAdapter.disable()
                onResume()
            }else{
                var bluetoothintent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivity(bluetoothintent)
                onResume()
            }
        })
    }
}