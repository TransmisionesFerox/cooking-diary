package com.example.finalproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.adapter.RecipeListAdapter
import com.example.finalproject.databinding.FragmentRecipeListBinding
import com.example.finalproject.model.entity.RecipeSearchResponse
import com.example.finalproject.model.network.createApiService

import retrofit2.Call
import retrofit2.Callback

class RecipeListFragment : Fragment(){
    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private var _binding: FragmentRecipeListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter = RecipeListAdapter();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupUI()

        val service = createApiService()
        binding.searchView.setIconified(false)
        binding.searchView.queryHint = "Введите запрос для поиска"
        binding.searchView.requestFocus()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                service.searchRecipes(query, true).enqueue(object : Callback<RecipeSearchResponse> {
                    override fun onResponse(call: retrofit2.Call<RecipeSearchResponse>, response: retrofit2.Response<RecipeSearchResponse>) {
                        if (response.isSuccessful) {
                            val recipes = response.body()?.results

                            recipes?.forEach {
                                adapter.updateItems(recipes)
                            }
                        } else {
                            println("Response was not successful")
                        }

                    }
                    override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                    }
                })
                return true
            }
        })
    }


    private fun setupUI() {
        with(binding) {
            eatList.layoutManager = LinearLayoutManager(context)
            eatList.adapter = adapter
        }
    }
}