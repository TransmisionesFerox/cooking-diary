package com.example.finalproject.model.entity

data class RecipeSteps(
    val steps: List<RecipeStep>,
)

data class RecipeStep(
    val equipment: List<RecipeEquipment>,
    val ingredients: List<RecipeIngredient>,
    val number: Int,
    val step: String,
)
data class RecipeEquipment(
    val image: String
)
data class RecipeIngredient(
    val image: String,
    val name: String
)