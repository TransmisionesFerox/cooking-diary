package com.example.finalproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.di.createApiService
import com.example.finalproject.model.entity.RecipeSteps
import com.example.finalproject.repository.RecipeRepository
import kotlinx.coroutines.launch

class StepsViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _steps = MutableLiveData<List<RecipeSteps>>()
    val steps: LiveData<List<RecipeSteps>> get() = _steps

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    constructor() : this(RecipeRepository(createApiService()))
    fun loadSteps(recipeId: String) {
        viewModelScope.launch {
            try {
                val steps = recipeRepository.getStepsById(recipeId)
                _steps.value = steps
            } catch (e: Exception) {
                _error.value = "Error fetching recipe steps: ${e.message}"
            }
        }
    }
}
