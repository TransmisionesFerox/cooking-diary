package com.example.finalproject.repository
import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.model.entity.RecipeSearchResponse
import com.example.finalproject.model.entity.RecipeSteps
import com.example.finalproject.model.network.RecipeService
class RecipeRepository(private val recipeService: RecipeService) {

    suspend fun searchRecipes(query: String, addRecipeInformation: Boolean): RecipeSearchResponse {
        return recipeService.searchRecipes(query, addRecipeInformation)
    }

    suspend fun getRecipeById(recipeId: String): RecipeDetail {
        return recipeService.getRecipeById(recipeId)
    }

    suspend fun getStepsById(recipeId: String): List<RecipeSteps> {
        val steps = recipeService.getStepsById(recipeId)
        return steps ?: emptyList()
    }
}
