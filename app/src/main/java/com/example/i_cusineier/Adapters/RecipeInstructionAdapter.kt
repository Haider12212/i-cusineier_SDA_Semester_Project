package com.example.i_cusineier.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.i_cusineier.R
import org.json.JSONObject

class RecipeInstructionAdapter(private val context: Context, private val steps: List<JSONObject>) : RecyclerView.Adapter<RecipeInstructionAdapter.StepViewHolder>() {

    class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stepNumberTextView: TextView = itemView.findViewById(R.id.stepNumberTextView)
        val stepDescriptionTextView: TextView = itemView.findViewById(R.id.stepDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.recipe_instruction_items, parent, false)
        return StepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]

        holder.stepNumberTextView.text = "Step ${step.optInt("number")}"
        holder.stepDescriptionTextView.text = step.optString("step")

        // You can set the image using Glide or other image-loading libraries
        // Assuming the image URL is in the "image" field of the step JSON object
        // For example, if you have an ImageView in your layout with ID stepImageView:
        // val stepImageView: ImageView = holder.itemView.findViewById(R.id.stepImageView)
        // Glide.with(holder.itemView.context)
        //     .load(step.optString("image"))
        //     .placeholder(R.drawable.placeholder_image) // You can set a placeholder image
        //     .into(stepImageView)
    }

    override fun getItemCount(): Int {
        return steps.size
    }
}
