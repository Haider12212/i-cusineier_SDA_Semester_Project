import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class Recipe(private val callback: RecipeCallback) {

    interface RecipeCallback {
        fun onRecipeReceived(recipes: JSONArray)
        fun onFailure(error: String)
    }

    fun getRecipe(ingredients: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=$ingredients&number=5&ignorePantry=true&ranking=1")
            .get()
            .addHeader("X-RapidAPI-Key", "bc4a01d91dmsha5cc637f3f1800dp1c1adfjsn2ddebc871447")
            .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val responseBody = response.body?.string()
                        val recipes = JSONArray(responseBody)
                        callback.onRecipeReceived(recipes)
                    } catch (e: Exception) {
                        callback.onFailure("Error parsing response")
                    }
                } else {
                    callback.onFailure("Request failed with code ${response.code}")
                }
            }
        })
    }
}
