package com.example.finalproject.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchRecipes(query: String, addRecipeInformation: Boolean) {
        viewModelScope.launch {
            try {
                val response = repository.searchRecipes(query, addRecipeInformation)
                _recipes.value = response.results
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    // Остальные методы для получения деталей рецепта и шагов рецепта
}
