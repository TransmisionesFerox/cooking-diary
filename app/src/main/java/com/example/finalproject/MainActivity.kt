package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.fragment.RecipeListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, RecipeListFragment.newInstance())
            .commit()

    }
//private val sharedPrefs by lazy {
    //getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    //}
    //override fun onCreate(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // Сохранение значений
        //val editor = sharedPrefs.edit()
        //editor.putString("username", "Anupam")
        //editor.putLong("l", 100L)
        //editor.apply()

        // Получение значений
        //val username = sharedPrefs.getString("username", "defaultName")
        //val lValue = sharedPrefs.getLong("l", 1L)

        // Используйте полученные значения в вашем приложении
        // Например, отобразите имя пользователя или другие данные
    }


