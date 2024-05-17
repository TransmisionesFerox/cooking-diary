package com.example.finalproject.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.repository.RecipeRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipeDetail = MutableLiveData<RecipeDetail>()
    val recipeDetail: LiveData<RecipeDetail> get() = _recipeDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadRecipeDetail(recipeId: String) {
        viewModelScope.launch {
            try {
                val detail = repository.getRecipeById(recipeId)
                _recipeDetail.value = detail
            } catch (e: Exception) {
                _error.value = "Error fetching recipe details: ${e.message}"
            }
        }
    }
}
