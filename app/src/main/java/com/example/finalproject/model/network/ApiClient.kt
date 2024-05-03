package com.example.finalproject.model.network

import com.example.finalproject.model.entity.RecipeSearchResponse
import com.example.finalproject.model.entity.RecipeDetail
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

    @Headers("X-Api-Key: e955606fb6764069a224f602de2d7e35")
    @GET("recipes/{id}/information")
    fun getRecipeById(@Path("id") recipeId: String): Call<RecipeDetail>
}

object ApiClient {
    private const val BASE_URL = "https://api.spoonacular.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val createApiService: RecipeService = retrofit.create(RecipeService::class.java)
}