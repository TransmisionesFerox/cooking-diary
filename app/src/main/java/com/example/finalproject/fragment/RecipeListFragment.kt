package com.example.finalproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapter.RecipeListAdapter
import com.example.finalproject.databinding.FragmentRecipeListBinding
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.entity.RecipeSearchResponse
import com.example.finalproject.model.interfaces.OnRecipeClickListener
import com.example.finalproject.model.network.ApiClient.createApiService

import retrofit2.Call
import retrofit2.Callback

class RecipeListFragment : Fragment(), OnRecipeClickListener{
    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private var _binding: FragmentRecipeListBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: RecipeListAdapter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        adapter = RecipeListAdapter(this)
        _binding!!.eatList.adapter = adapter
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupUI()


        binding.searchView.setIconified(false)
        binding.searchView.queryHint = "Введите запрос для поиска"
        binding.searchView.requestFocus()

        loadRecipes("")

        setupSearchListener()


    }

    private fun loadRecipes(query: String) {
        val service = createApiService
        service.searchRecipes(query, true).enqueue(object : Callback<RecipeSearchResponse> {
            override fun onResponse(call: retrofit2.Call<RecipeSearchResponse>, response: retrofit2.Response<RecipeSearchResponse>) {
                if (response.isSuccessful) {

                    val recipes = response.body()?.results

                    if(recipes?.size == 0){
                        adapter.submitList(recipes)
                        Toast.makeText(context, "Такого блюда не найдено", Toast.LENGTH_SHORT).show()
                    } else{
                        adapter.submitList(recipes)
                    }

                } else {
                    println("Response was not successful")
                }


            }
            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSearchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                loadRecipes(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
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

    override fun onRecipeClicked(recipe: Recipe) {
        val fragment = DetailFragment.newInstance(recipe.id)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_view, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}