package com.example.forhealthfullapp
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.forhealthfullapp.bluetooth.Devices
import kotlinx.android.synthetic.main.splash_screen.*


class SplashScreen : AppCompatActivity() {

    var address: String? = null
    var deviceName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide Top notification bar

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.splash_screen)

        // hide bottom navigation bar

         val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
         or View.SYSTEM_UI_FLAG_FULLSCREEN)

         val decorView = window.decorView
         decorView.systemUiVisibility = uiOptions


        val newint = intent
        deviceName = newint.getStringExtra(Devices.EXTRA_NAME)
        address = newint.getStringExtra(Devices.EXTRA_ADDRESS)

        val homeIntent = Intent(this@SplashScreen, FirstPage::class.java)

        getstartbutton.setOnClickListener(View.OnClickListener {

            homeIntent.putExtra(Devices.EXTRA_NAME, deviceName)
            homeIntent.putExtra(Devices.EXTRA_ADDRESS, address)
            startActivity(homeIntent)
            finish()
        })

        // start new intent after given time

        // val splashScreenTimeout = 4000
        // val homeIntent = Intent(this@SplashScreen, FirstPage::class.java)
        // Handler().postDelayed({
        // startActivity(homeIntent)
        // finish()
        // }, splashScreenTimeout.toLong())


    }
}