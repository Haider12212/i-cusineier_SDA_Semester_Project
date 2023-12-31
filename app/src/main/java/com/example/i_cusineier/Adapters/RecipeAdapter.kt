import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.i_cusineier.R
import org.json.JSONArray
import org.json.JSONObject

class RecipeAdapter(private val context: Context, private val recipes: JSONArray) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
        // Add other TextViews for additional information as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes.getJSONObject(position)

        // Set thumbnail using Glide library
        Glide.with(context)
            .load(recipe.getString("image"))
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.thumbnailImageView)

        holder.titleTextView.text = recipe.getString("title")

        // Extract nutrition information
        val nutrientsArray = recipe.getJSONArray("Nutrients")

        // Assuming "calories" is one of the nutrients
        val nutrientsJSONObject = nutrientsArray.getJSONObject(0)
        val calories = nutrientsJSONObject.getString("amount")
        holder.caloriesTextView.setText(calories);

    }

    override fun getItemCount(): Int {
        return recipes.length()
    }
}
