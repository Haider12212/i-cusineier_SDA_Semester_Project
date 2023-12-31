package com.example.i_cusineier

import Recipe
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.i_cusineier.Adapters.RecipeInstructionAdapter
import org.json.JSONArray
import org.json.JSONObject

class Recipedetails:AppCompatActivity() {
    private lateinit var instructionRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipedetails)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        // Get recipe details from the intent
        instructionRecyclerView = findViewById(R.id.stepsRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        instructionRecyclerView.layoutManager = layoutManager

        val viewInstruction: Button = findViewById(R.id.viewInstructionsButton)

        val recipeString = intent.getStringExtra("recipe")
        if (recipeString != null) {
            val recipe = JSONObject(recipeString)

            // Display recipe details
            val titleTextView: TextView = findViewById(R.id.detailsTitleTextView)
            val thumbnailImageView: ImageView = findViewById(R.id.detailsThumbnailImageView)

            // Extract and display the recipe ID, title, and image
            val recipeId = recipe.optInt("id")
            titleTextView.text = recipe.optString("title")

            // Load and display the recipe image using Glide
            Glide.with(this)
                .load(recipe.optString("image"))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnailImageView)

            val placeholderAdapter = RecipeInstructionAdapter(this, emptyList())
            instructionRecyclerView.adapter = placeholderAdapter

            viewInstruction.setOnClickListener {
                val recipeObj = Recipe(object : Recipe.RecipeCallback {
                    override fun onRecipeReceived(recipes: JSONArray) {
                        // Inside Recipe_Details_Activity
                        runOnUiThread {
                            val steps = getStepsFromJson(recipes)
                            val adapter = RecipeInstructionAdapter(this@Recipedetails, steps)
                            instructionRecyclerView.adapter = adapter


                        }
                    }

                    override fun onFailure(error: String) {
                        // Handle failure, if needed
                    }
                })

                recipeObj.getInstruction(recipeId)
            }
        }
    }

    private fun getStepsFromJson(recipe: JSONArray): List<JSONObject> {
        val stepsList = mutableListOf<JSONObject>()

        // Iterate through each recipe object in the array
        for (i in 0 until recipe.length()) {
            val recipeObject = recipe.optJSONObject(i)
            if (recipeObject != null && recipeObject.has("steps")) {
                // Access the "steps" array inside each recipe
                val stepsArray = recipeObject.optJSONArray("steps")

                if (stepsArray != null) {
                    // Iterate through each step in the "steps" array
                    for (j in 0 until stepsArray.length()) {
                        val stepObject = stepsArray.optJSONObject(j)
                        if (stepObject != null) {
                            stepsList.add(stepObject)
                        }
                    }
                }
            }
        }

        return stepsList
    }

}
