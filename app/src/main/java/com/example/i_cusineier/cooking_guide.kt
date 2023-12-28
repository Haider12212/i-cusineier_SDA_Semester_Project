package com.example.i_cusineier

import Recipe
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class cooking_guide : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_guide)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        val button:Button = findViewById(R.id.submitButton)
        button.setOnClickListener {
            val editText: EditText = findViewById(R.id.searchEditText)
            val query = editText.text.toString()

            val newRecipe = Recipe()
            val response = newRecipe.getRecipe(query)

            // Log the response
            Log.d("RecipeResponse", (response ?: "Response is null").toString())
        }


    }
}
