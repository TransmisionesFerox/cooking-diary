package com.example.finalproject.model.network

import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.entity.RecipeDetail
//import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.model.entity.RecipeSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @Headers("X-Api-Key: e955606fb6764069a224f602de2d7e35")
    @GET("recipes/complexSearch")
    fun searchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean
    ): Call<RecipeSearchResponse>

    @GET("recipes/{id}/information")
    fun getRecipeById(@Path("id") recipeId: String): Call<RecipeDetail>
}

fun createDetailApiService(): RecipeService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(RecipeService::class.java)
}
