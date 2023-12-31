package com.example.i_cusineier

import MyPreferences
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyRecipes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_recipes)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView1)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Retrieve all recipes
        val fetchedRecipes = MyPreferences.getAllRecipes(this)

        // Check if the fetched recipe list is not empty
        if (fetchedRecipes.isNotEmpty()) {
            // Populate your RecyclerView with all the fetched recipes
            val adapter = SavedRecipeAdapter(this,fetchedRecipes)
            recyclerView.adapter = adapter
        } else {
            // Handle the case where there are no saved recipes
            // You can display a message or take other actions
        }
    }
}
