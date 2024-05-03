package com.example.finalproject.model.entity

import android.os.Parcelable
import java.io.Serializable

data class Recipe(
    val id: String,
    val title: String,
    val image: String,
    val summary: String
): Serializable

data class RecipeSearchResponse(
    val offset: Int,
    val number: Int,
    val results: List<Recipe>,
    val totalResults: Int
)