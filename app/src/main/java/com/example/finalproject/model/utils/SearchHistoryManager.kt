package com.example.finalproject.model.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SearchHistoryManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val historyKey = "queries"

    fun saveQuery(query: String) {
        val queries = getQueries().toMutableList()
        if (queries.contains(query)) {
            queries.remove(query)
        }
        queries.add(0, query)
        if (queries.size > 5) {
            queries.removeAt(5)
        }
        val json = gson.toJson(queries)
        sharedPreferences.edit().putString(historyKey, json).apply()
    }

    fun getQueries(): List<String> {
        val json = sharedPreferences.getString(historyKey, null)
        return if (json != null) {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun clearQueries() {
        sharedPreferences.edit().remove(historyKey).apply()
    }
}