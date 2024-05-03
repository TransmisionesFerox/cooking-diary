package com.example.finalproject.model.entity

import java.io.Serializable

data class RecipeDetail(
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