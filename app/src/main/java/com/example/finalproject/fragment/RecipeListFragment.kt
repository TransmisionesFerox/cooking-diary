package com.example.finalproject.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.activities.RecipeDetailsActivity
import com.example.finalproject.adapter.RecipeListAdapter
import com.example.finalproject.databinding.FragmentRecipeListBinding
import com.example.finalproject.model.entity.RecipeSearchResponse
import com.example.finalproject.model.network.createDetailApiService
import com.example.finalproject.adapter.RecipeListAdapter.OnItemClickListener
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.entity.RecipeDetails

import retrofit2.Call
import retrofit2.Callback
import java.io.Serializable

class RecipeListFragment : Fragment(){
    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private var _binding: FragmentRecipeListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter = RecipeListAdapter();
    private fun setupRecyclerView() {
        // Set the adapter to the RecyclerView first
        binding.eatList.adapter = adapter

        // Then set up the click listener and RecyclerView configuration
        val adapterClickListener = object : OnItemClickListener {
            override fun onRecipeClicked(recipe: Recipe) {
                // Handle recipe click here (e.g., open recipe details activity)
                val intent = Intent(requireContext(), RecipeDetailsActivity::class.java)
                intent.putExtra("recipeDetails", recipe as Serializable)
                startActivity(intent)
                Log.d("RecipeListFragment", "Recipe clicked: ${recipe.title}")
            }
        }

        binding.eatList.layoutManager = LinearLayoutManager(context)
    }
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
        setupRecyclerView()
        val service = createDetailApiService()
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
                            println(recipes)
                            recipes?.forEach {
                                adapter.updateItems(recipes)
                            }
                        } else {
                            val responseCode = response.code()
                            val errorMessage = response.errorBody()?.string()
                            println("Response not successful: code = $responseCode, message = $errorMessage")
//                            println("Response was not successful")
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