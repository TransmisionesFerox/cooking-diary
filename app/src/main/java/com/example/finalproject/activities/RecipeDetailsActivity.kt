package com.example.finalproject.activities
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.adapter.IngredientAdapter
import com.example.finalproject.model.entity.Ingredient
import com.example.finalproject.model.entity.RecipeDetails

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var recipeImage: ImageView
    private lateinit var recipeTitle: TextView
    private lateinit var recipeSummary: TextView
    private lateinit var cookingTimeLabel: TextView  // Update variable name for clarity
    private lateinit var cookingTime: TextView
    private lateinit var ingredientsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_details) // Use your layout file

        // Find view references
        recipeImage = findViewById(R.id.recipe_image)
        recipeTitle = findViewById(R.id.recipe_title)
        recipeSummary = findViewById(R.id.recipe_summary)
        cookingTimeLabel = findViewById(R.id.cooking_time)  // Update variable name
        cookingTime = findViewById(R.id.cooking_time)
        ingredientsList = findViewById(R.id.ingredients_list)

        // Retrieve recipe data from Intent
        val recipe = intent.getSerializableExtra("recipeDetails") as? RecipeDetails

        // Display recipe details
        if (recipe != null) {
            recipeTitle.text = recipe.title
            recipeSummary.text = recipe.summary // Assuming description is mapped to recipeSummary
            // Handle image loading (consider using a library like Glide or Picasso)
            // ... (replace with your image loading logic)

            // Format and display cooking time (if available)
            val formattedCookingTime = "${recipe.readyInMinutes} minutes"
            cookingTime.text = formattedCookingTime

            // Set up ingredients list
            ingredientsList.layoutManager = LinearLayoutManager(this)
            val adapter = IngredientAdapter(recipe.ingredients)
            ingredientsList.adapter = adapter
        } else {
            // Handle case where recipe is null (e.g., display error message)
            // ...
        }
    }
}