package com.example.i_cusineier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1 :ImageButton = findViewById(R.id.button1);
        button1.setOnClickListener {
            // Apply a fade-in animation to the button
            applyClickEffect(button1)

            // Redirect to the SecondActivity
            val intent = Intent(this@MainActivity, Add_device::class.java)
            startActivity(intent)
        }
        val button2 :ImageButton = findViewById(R.id.button2);
        button2.setOnClickListener {
            // Apply a fade-in animation to the button
            applyClickEffect(button2)

            // Redirect to the SecondActivity
            val intent = Intent(this@MainActivity, Cookingguide::class.java)
            startActivity(intent)
        }
    }

    // Method to apply a fade-in animation to a view
    private fun applyClickEffect(view: View) {
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.duration = 300
        view.startAnimation(fadeIn)
    }

}