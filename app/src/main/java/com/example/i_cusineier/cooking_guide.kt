package com.example.i_cusineier

import Recipe
import RecipeAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray

class cooking_guide : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_guide)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val button: Button = findViewById(R.id.submitButton)
        button.setOnClickListener {
            val editText: EditText = findViewById(R.id.searchEditText)
            val query = editText.text.toString()

            val newRecipe = Recipe(object : Recipe.RecipeCallback {
                override fun onRecipeReceived(recipes: JSONArray) {
                    // Handle the received recipes
                    runOnUiThread {
                        val adapter = RecipeAdapter(this@cooking_guide, recipes)
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@cooking_guide, "Request failed: $error", Toast.LENGTH_SHORT).show()
                    }
                }

            })

            // Initiate the asynchronous request
            newRecipe.getRecipe(query)
        }
    }
}
