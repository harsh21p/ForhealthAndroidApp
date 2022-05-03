package com.example.forhealthfullapp

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.physiologin.*

class PhysioLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.btconectivity)

        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions


        backbtnp.setOnClickListener(View.OnClickListener {
            val backIntent = Intent(this@PhysioLogin, SecondPage::class.java)
            startActivity(backIntent)
            finish()
        })

        login.setOnClickListener(View.OnClickListener {
            val selectIntent = Intent(this@PhysioLogin, Paireddeviceslist::class.java)
            startActivity(selectIntent)
            finish()
        })
    }




}