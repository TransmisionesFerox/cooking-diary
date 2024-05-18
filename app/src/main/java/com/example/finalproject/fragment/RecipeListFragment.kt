package com.example.finalproject.fragment

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapter.RecipeListAdapter
import com.example.finalproject.databinding.FragmentRecipeListBinding
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.interfaces.OnRecipeClickListener
import com.example.finalproject.ViewModel.RecipeViewModel
import com.example.finalproject.model.utils.SearchHistoryManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment(), OnRecipeClickListener {

    private val viewModel: RecipeViewModel by viewModel()
    private lateinit var searchHistoryManager: SearchHistoryManager


    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecipeListAdapter
    private lateinit var suggestionsAdapter: SimpleCursorAdapter

    private val MAX_SUGGESTIONS_COUNT = 3

    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchHistoryManager = SearchHistoryManager(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        adapter = RecipeListAdapter(this)
        binding.eatList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        binding.searchView.setIconified(false)
        binding.searchView.queryHint = "Введите запрос для поиска"
        binding.searchView.requestFocus()

        setupSearchListener()

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            adapter.submitList(recipes)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.searchRecipes("", true)

        updateSearchSuggestions()
    }

    private fun setupSearchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchHistoryManager.saveQuery(query)
                viewModel.searchRecipes(query,true)
                updateSearchSuggestions()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchRecipes(query,true)
                return true
            }
        })

    }
    private fun updateSearchSuggestions() {
        val queries = searchHistoryManager.getQueries().take(MAX_SUGGESTIONS_COUNT)
        val cursor = createCursorFromQueries(queries)
        suggestionsAdapter.changeCursor(cursor)
    }



    private fun setupUI() {
        with(binding) {
            eatList.layoutManager = LinearLayoutManager(context)
            eatList.adapter = adapter
        }

        suggestionsAdapter = SimpleCursorAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            null,
            arrayOf("query"),
            intArrayOf(android.R.id.text1),
            0
        )
        binding.searchView.suggestionsAdapter = suggestionsAdapter

        binding.searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                val cursor = suggestionsAdapter.cursor
                cursor.moveToPosition(position)
                val suggestion = cursor.getString(cursor.getColumnIndexOrThrow("query"))
                binding.searchView.setQuery(suggestion, false)
                viewModel.searchRecipes(suggestion, true)
                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = suggestionsAdapter.cursor
                cursor.moveToPosition(position)
                val suggestion = cursor.getString(cursor.getColumnIndexOrThrow("query"))
                binding.searchView.setQuery(suggestion, false)
                viewModel.searchRecipes(suggestion, true)
                return true
            }
        })
    }
    private fun createCursorFromQueries(queries: List<String>): Cursor {
        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, "query"))
        queries.forEachIndexed { index, query ->
            cursor.addRow(arrayOf(index, query))
        }
        return cursor
    }


    override fun onRecipeClicked(recipe: Recipe) {
        val fragment = DetailFragment.newInstance(recipe.id)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_view, fragment)
            ?.addToBackStack(null)
            ?.commit()

        searchHistoryManager.clearQueries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
