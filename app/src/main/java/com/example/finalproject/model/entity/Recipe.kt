package com.example.finalproject.model.entity

data class Recipe(
    val id: String,
    val title: String,
    val image: String,
    val summary: String
)

data class RecipeSearchResponse(
    val offset: Int,
    val number: Int,
    val results: List<Recipe>,
    val totalResults: Int
)