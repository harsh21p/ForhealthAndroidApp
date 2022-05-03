package com.example.forhealthfullapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.physiologin.*
import kotlinx.android.synthetic.main.physiologin.physiotherapistcard
import kotlinx.android.synthetic.main.secondscreen.*


class SecondPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondscreen)

        physiotherapistcard.setOnClickListener(View.OnClickListener {
            val PhysioIntent = Intent(this@SecondPage, PhysioLogin::class.java)
            startActivity(PhysioIntent)
        })

        patientcard.setOnClickListener(View.OnClickListener {
            val PatientIntent = Intent(this@SecondPage, PatientLogin::class.java)
            startActivity(PatientIntent)
        })

    }
}