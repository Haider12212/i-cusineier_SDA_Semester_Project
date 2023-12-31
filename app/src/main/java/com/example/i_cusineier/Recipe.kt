import android.util.Log
import okhttp3.*
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
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=$ingredients&number=20")
            .get()
            .addHeader("X-RapidAPI-Key", "b8008c0c06msha61af95bef093d0p1aa4bcjsnf992ba55bbe6")
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

    fun getInstruction(recipeId: Int) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/$recipeId/analyzedInstructions?stepBreakdown=true")
            .get()
            .addHeader("X-RapidAPI-Key", "b8008c0c06msha61af95bef093d0p1aa4bcjsnf992ba55bbe6")
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
                        // Handle the instruction response as needed
                        Log.d("RecipeInstruction", responseBody ?: "Empty response")
                        val recipes = JSONArray(responseBody)
                        callback.onRecipeReceived(recipes)
                    } catch (e: Exception) {
                        Log.e("RecipeInstruction", "Error parsing instruction response", e)
                    }
                } else {
                    Log.e("RecipeInstruction", "Request failed with code ${response.code}")
                }
            }
        })
    }
}
