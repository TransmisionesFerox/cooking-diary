package com.example.finalproject.model.di

import com.example.finalproject.ViewModel.DetailViewModel
import com.example.finalproject.repository.RecipeRepository
import com.example.finalproject.ViewModel.RecipeViewModel
import com.example.finalproject.ViewModel.StepsViewModel
import com.example.finalproject.model.network.RecipeService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { RecipeRepository(get()) }
    viewModel { RecipeViewModel(get()) }
    viewModel { StepsViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    single { createApiService() }
}
val viewModelModule = module {
    viewModel { StepsViewModel(get())}
}

fun createApiService(): RecipeService {
    return Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(RecipeService::class.java)
}