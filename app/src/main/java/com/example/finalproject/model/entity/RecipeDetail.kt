package com.example.finalproject.model.entity

data class RecipeDetail(
    val id: String,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val extendedIngredients: List<Ingredient>
)

data class Ingredient(
    val id: Int,
    val name: String,
    val amount: Double,
    val measures: Measures
)
data class Measures(
    val us: Measure
)
data class Measure(
    val unitShort: String
)