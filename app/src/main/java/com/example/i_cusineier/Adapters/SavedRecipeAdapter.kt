package com.example.i_cusineier

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject

class SavedRecipeAdapter(private val context: Context,private val recipes: List<MyPreferences.FetchedRecipe>) : RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.recipeNameTextView)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImageTextView)
        val instructions: TextView = itemView.findViewById(R.id.instructionsTextView)
        val deletebutton:Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.saved_recipe_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]

        Glide.with(context)
            .load(recipe.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.recipeImage)

        holder.recipeName.text = recipe.recipeName
        holder.deletebutton.setOnClickListener {
            // Call the delete function when the delete button is clicked
            MyPreferences.deleteRecipe(context, position)
            // Notify the adapter that the data has changed
            notifyItemRemoved(position)

        }
        val instructionsList = recipe.instructions

        if (instructionsList.isNotEmpty()) {
            val instructionsStringBuilder = StringBuilder()

            for (stepObject in instructionsList) {
                val stepText = stepObject.optString("step", "")

                if (!stepText.isNullOrBlank()) {
                    instructionsStringBuilder.append("Steps:\n")
                    instructionsStringBuilder.append("$stepText\n")

                    // Display ingredients
                    if (stepObject.has("ingredients")) {
                        instructionsStringBuilder.append("Ingredients:\n")
                        val ingredientsArray = stepObject.getJSONArray("ingredients")
                        for (i in 0 until ingredientsArray.length()) {
                            val ingredient = ingredientsArray.optJSONObject(i)
                            val ingredientName = ingredient.optString("name")
                            instructionsStringBuilder.append("   - $ingredientName\n")
                        }
                    }

                    // Display equipment
                    if (stepObject.has("equipment")) {
                        instructionsStringBuilder.append("Equipment:\n")
                        val equipmentArray = stepObject.getJSONArray("equipment")
                        for (i in 0 until equipmentArray.length()) {
                            val equipment = equipmentArray.optJSONObject(i)
                            val equipmentName = equipment.optString("name")
                            instructionsStringBuilder.append("   - $equipmentName\n")
                        }
                    }

                    // Display length
                    if (stepObject.has("length")) {
                        instructionsStringBuilder.append("Length:\n")
                        val lengthObject = stepObject.optJSONObject("length")
                        val lengthNumber = lengthObject.optInt("number", -1)
                        val lengthUnit = lengthObject.optString("unit")
                        instructionsStringBuilder.append("   - $lengthNumber $lengthUnit\n")
                    }

                    // Add a newline to separate steps
                    instructionsStringBuilder.append("\n")
                }
            }

            val finalInstructions = instructionsStringBuilder.toString().trim()
            if (finalInstructions.isNotEmpty()) {
                holder.instructions.text = finalInstructions
            } else {
                holder.instructions.text = "No instructions available"
            }
        } else {
            holder.instructions.text = "No instructions available"
        }
    }



    override fun getItemCount(): Int {
        return recipes.size
    }


}
