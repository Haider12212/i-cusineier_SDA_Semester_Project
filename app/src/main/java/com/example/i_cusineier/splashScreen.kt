package com.example.i_cusineier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)
        Handler().postDelayed({
            // Start the main activity after the splash duration
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // close the splash activity
        }, 500)
    }
}