package com.example.finalproject.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapter.RecipeListAdapter
import com.example.finalproject.adapter.RecipeListAdapter.OnItemClickListener
import com.example.finalproject.databinding.FragmentRecipeListBinding
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.network.createDetailApiService
import com.example.finalproject.model.entity.RecipeSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private val adapter = RecipeListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        binding.eatList.layoutManager = LinearLayoutManager(requireContext())
        binding.eatList.adapter = adapter

        adapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onRecipeClicked(recipe: Recipe) {
                // Создать экземпляр DetailFragment и добавить его в контейнер
                val detailFragment = DetailFragment.newInstance(recipe.id)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.apply {
            isIconified = false
            queryHint = "Введите запрос для поиска"
            requestFocus()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    val service = createDetailApiService()
                    service.searchRecipes(query, true).enqueue(object : Callback<RecipeSearchResponse> {
                        override fun onResponse(call: Call<RecipeSearchResponse>, response: Response<RecipeSearchResponse>) {
                            if (response.isSuccessful) {
                                val recipes = response.body()?.results
                                recipes?.let {
                                    adapter.updateItems(it)
                                }
                            } else {
                                val responseCode = response.code()
                                val errorMessage = response.errorBody()?.string()
                                Log.e("RecipeListFragment", "Response not successful: code = $responseCode, message = $errorMessage")
                            }
                        }

                        override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                            Log.e("RecipeListFragment", "Failed to fetch recipes", t)
                        }
                    })
                    return true
                }
            })
        }
    }
    companion object {
        fun newInstance(): RecipeListFragment {
            return RecipeListFragment()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}