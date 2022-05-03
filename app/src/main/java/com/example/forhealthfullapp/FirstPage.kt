package com.example.forhealthfullapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.forhealthfullapp.bluetooth.Devices
import kotlinx.android.synthetic.main.firstpage.*

class FirstPage : AppCompatActivity() {

    var address: String? = null
    var deviceName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide Top notification bar

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.firstpage)

        // hide bottom navigation bar

        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        val decorView = window.decorView
        decorView.systemUiVisibility = uiOptions

        val newint = intent
        deviceName = newint.getStringExtra(Devices.EXTRA_NAME)
        address = newint.getStringExtra(Devices.EXTRA_ADDRESS)

        proceedbutton.setOnClickListener(View.OnClickListener {
            val FirstIntent = Intent(this@FirstPage,Control_Panal::class.java)
            FirstIntent.putExtra(Devices.EXTRA_NAME, deviceName)
            FirstIntent.putExtra(Devices.EXTRA_ADDRESS, address)
            startActivity(FirstIntent)
            finish()
        })

    }
}