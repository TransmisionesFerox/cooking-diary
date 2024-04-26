package com.example.finalproject.model.entity

import android.os.Parcelable
import java.io.Serializable

data class Recipe(
    val id: String,
    val title: String,
    val image: String,
    val summary: String
)
data class RecipeDetails(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val imageType: String,
    val readyInMinutes: String,
    val ingredients: List<Ingredient> = emptyList()
): Serializable
data class Ingredient(
    val id: Int,
    val name: String,
    val quantity: Double
)

data class RecipeSearchResponse(
    val offset: Int,
    val number: Int,
    val results: List<Recipe>,
    val totalResults: Int
)