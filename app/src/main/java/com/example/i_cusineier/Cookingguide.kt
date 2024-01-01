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
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

class Cookingguide : AppCompatActivity(),RecipeAdapter.OnRecipeItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_guide)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        progressBar.visibility = View.GONE

        val button: Button = findViewById(R.id.submitButton)
        button.setOnClickListener {
            // Close the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            // Your existing code for fetching recipes
            val editText: EditText = findViewById(R.id.searchEditText)
            val query = editText.text.toString()
            progressBar.visibility = View.VISIBLE
            val newRecipe = Recipe(object : Recipe.RecipeCallback {
                override fun onRecipeReceived(recipes: JSONArray) {
                    // Handle the received recipes
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        val adapter = RecipeAdapter(this@Cookingguide, recipes)
                        adapter.setOnRecipeItemClickListener(this@Cookingguide)
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(error: String) {
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@Cookingguide, "Request failed: $error", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            // Initiate the asynchronous request
            newRecipe.getRecipe(query)
        }
    }

    override fun onRecipeItemClick(recipe: JSONObject) {
        val intent = Intent(this, Recipedetails::class.java)

        // Pass the recipe details to the new activity
        intent.putExtra("recipe", recipe.toString())

        // Start the new activity
        startActivity(intent)
    }
}
