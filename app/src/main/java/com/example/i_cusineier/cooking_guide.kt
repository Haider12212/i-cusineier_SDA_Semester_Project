package com.example.i_cusineier

import Recipe
import RecipeAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

class cooking_guide : AppCompatActivity(),RecipeAdapter.OnRecipeItemClickListener {

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
            // Close the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            // Your existing code for fetching recipes
            val editText: EditText = findViewById(R.id.searchEditText)
            val query = editText.text.toString()

            val newRecipe = Recipe(object : Recipe.RecipeCallback {
                override fun onRecipeReceived(recipes: JSONArray) {
                    // Handle the received recipes
                    runOnUiThread {
                        val adapter = RecipeAdapter(this@cooking_guide, recipes)
                        adapter.setOnRecipeItemClickListener(this@cooking_guide)
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

    override fun onRecipeItemClick(recipe: JSONObject) {
        val intent = Intent(this, Recipe_Details_Activity::class.java)

        // Pass the recipe details to the new activity
        intent.putExtra("recipe", recipe.toString())

        // Start the new activity
        startActivity(intent)
    }
}
