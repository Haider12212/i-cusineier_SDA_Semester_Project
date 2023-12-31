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
    private var listener: OnRecipeItemClickListener? = null

    class ViewHolder(itemView: View,) : RecyclerView.ViewHolder(itemView) {
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

        // Set the click listener for the item
        holder.itemView.setOnClickListener {
            listener?.onRecipeItemClick(recipe)
        }

       holder.caloriesTextView.text = "Likes:" + recipe.getString("likes")
    }
    fun setOnRecipeItemClickListener(listener: OnRecipeItemClickListener) {
        this.listener = listener
    }


    override fun getItemCount(): Int {
        return recipes.length()
    }

    interface OnRecipeItemClickListener {
        fun onRecipeItemClick(recipe: JSONObject)
    }

}
