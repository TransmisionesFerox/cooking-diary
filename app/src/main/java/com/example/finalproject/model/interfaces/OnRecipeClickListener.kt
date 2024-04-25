package com.example.finalproject.model.interfaces

import com.example.finalproject.model.entity.Recipe

interface OnRecipeClickListener {
    fun onRecipeClicked(recipe: Recipe)
}