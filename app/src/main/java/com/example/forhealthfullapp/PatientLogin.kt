package com.example.forhealthfullapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.patientlogin.*

class PatientLogin  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientlogin)

        backbtnpa.setOnClickListener(View.OnClickListener {
            val BackIntent = Intent(this@PatientLogin, SecondPage::class.java)
            startActivity(BackIntent)
            finish()
        })
    }

}