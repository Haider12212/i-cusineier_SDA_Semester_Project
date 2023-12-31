import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object MyPreferences {
    private const val PREFS_NAME = "MyPrefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_RECIPE_NAME = "recipe_name"
    private const val KEY_RECIPE_IMAGE_URL = "recipe_image_url"
    private const val KEY_RECIPE_INSTRUCTIONS = "recipe_instructions"
    private const val KEY_RECIPE_SIZE = "";


    // Save user name to SharedPreferences
    fun saveUserName(context: Context, userName: String?) {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(KEY_USER_NAME, userName)
        editor.apply()
    }

    // Retrieve user name from SharedPreferences
    fun getUserName(context: Context): String? {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getString(KEY_USER_NAME, "")
    }

    // Save recipe details to SharedPreferences
    // Save recipe details to SharedPreferences
    fun saveRecipeDetails(
        context: Context,
        recipeName: String?,
        imageUrl: String?,
        instructions: List<JSONObject>?
    ) {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        // Get the current size of the saved recipes
        val currentSize = preferences.getInt(KEY_RECIPE_SIZE, 0)

        // Append the new data to the existing data
        editor.putString(KEY_RECIPE_NAME + currentSize, recipeName)
        editor.putString(KEY_RECIPE_IMAGE_URL + currentSize, imageUrl)

        // Convert the list of JSONObjects to a JSON array string
        val instructionsArray = JSONArray(instructions)
        val instructionsString = instructionsArray.toString()
        editor.putString(KEY_RECIPE_INSTRUCTIONS + currentSize, instructionsString)

        // Increment the size of the saved recipes
        editor.putInt(KEY_RECIPE_SIZE, currentSize + 1)

        editor.apply()
    }

    // Retrieve recipe details from SharedPreferences
    fun getRecipeDetails(context: Context): FetchedRecipe {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val recipeName = preferences.getString(KEY_RECIPE_NAME, "")
        val imageUrl = preferences.getString(KEY_RECIPE_IMAGE_URL, "")
        val instructionsString = preferences.getString(KEY_RECIPE_INSTRUCTIONS, "")

        // Convert the JSON array string back to a list of JSON objects
        val instructionsArray = JSONArray(instructionsString)
        val instructionsList = mutableListOf<JSONObject>()
        for (i in 0 until instructionsArray.length()) {
            instructionsList.add(instructionsArray.getJSONObject(i))
        }

        return FetchedRecipe(recipeName, imageUrl, instructionsList)
    }
    // ...

    fun getAllRecipes(context: Context): List<FetchedRecipe> {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val recipeSize = preferences.getInt(KEY_RECIPE_SIZE, 0)

        val recipes = mutableListOf<FetchedRecipe>()

        for (i in 0 until recipeSize) {
            val recipeName = preferences.getString(KEY_RECIPE_NAME + i, "")
            val imageUrl = preferences.getString(KEY_RECIPE_IMAGE_URL + i, "")
            val instructionsString = preferences.getString(KEY_RECIPE_INSTRUCTIONS + i, "")

            // Convert the JSON array string back to a list of JSON objects
            val instructionsArray = JSONArray(instructionsString)
            val instructionsList = mutableListOf<JSONObject>()
            for (j in 0 until instructionsArray.length()) {
                instructionsList.add(instructionsArray.getJSONObject(j))
            }

            recipes.add(FetchedRecipe(recipeName, imageUrl, instructionsList))
        }

        return recipes
    }

// ...



    // Model class for recipe details
    class FetchedRecipe(val recipeName: String?, val imageUrl: String?, val instructions: List<JSONObject>)
}
