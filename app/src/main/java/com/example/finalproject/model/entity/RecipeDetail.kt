package com.example.finalproject.model.entity

import java.io.Serializable

data class RecipeDetail(
    val id: String,
    val title: String,
    val image: String,
    val readyInMinutes: Int
): Serializable