package com.example.finalproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.fragment.RecipeListFragment
import com.example.finalproject.model.di.appModule
import com.example.finalproject.model.di.viewModelModule
import com.example.finalproject.model.entity.Recipe
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
            modules(viewModelModule)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, RecipeListFragment.newInstance())
            .commit()

    }
    //val sharedPreferences = getSharedPreferences("my_pref_file", Context.MODE_PRIVATE)
    //fun addRecipeToFavorites(recipe: Recipe) {
        //val editor = sharedPreferences.edit()
        //editor.putBoolean(recipe.id.toString(), true) // Используйте уникальный идентификатор блюда в качестве ключа
        //editor.apply()
    //}

    //val editor = sharedPreferences.edit()
    //editor.putBoolean("is_favorite", true)
    //editor.apply()

    //val isFavorite = sharedPreferences.getBoolean("is_favorite", false)


}