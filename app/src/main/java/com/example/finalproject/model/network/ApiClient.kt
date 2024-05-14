package com.example.finalproject.model.network

import com.example.finalproject.model.entity.RecipeSearchResponse
import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.model.entity.RecipeSteps
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @Headers("X-Api-Key: e955606fb6764069a224f602de2d7e35")
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean
    ): RecipeSearchResponse

    @Headers("X-Api-Key: e955606fb6764069a224f602de2d7e35")
    @GET("recipes/{id}/information")
    suspend fun getRecipeById(@Path("id") recipeId: String): RecipeDetail

    @Headers("X-Api-Key: e955606fb6764069a224f602de2d7e35")
    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getStepsById(@Path("id") recipeId: String): List<RecipeSteps>

}
