package com.example.finalproject.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.adapter.IngridientListAdapter
import com.example.finalproject.model.network.RecipeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {
    private lateinit var ingredientsList: RecyclerView
    private val recipeService: RecipeService by inject()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed, container, false)
        val detailId = arguments?.getString("recipeId")
        ingredientsList = view.findViewById(R.id.ingridients)
        ingredientsList.layoutManager = LinearLayoutManager(context)

        if (detailId != null) {
            loadRecipeDetail(detailId, view)
        }

        val button: Button = view.findViewById(R.id.button_cook)

        button.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, StepsFragment.newInstance(detailId))
                .addToBackStack(null)
                .commit()
        }
        return view
    }

    private fun loadRecipeDetail(detailId: String, view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val recipeDetail = withContext(Dispatchers.IO) {
                    recipeService.getRecipeById(detailId)
                }
                view.findViewById<TextView>(R.id.detail_title).text = recipeDetail.title
                context?.let {
                    Glide.with(it)
                        .load(recipeDetail.image)
                        .into(view.findViewById<ImageView>(R.id.detail_image))
                }
                view.findViewById<TextView>(R.id.detail_time).text = recipeDetail.readyInMinutes.toString()
                val adapter = IngridientListAdapter()
                adapter.submitList(recipeDetail.extendedIngredients)
                ingredientsList.adapter = adapter
            } catch (e: Exception) {
                println("Error fetching recipe details: ${e.message}")
            }
        }
    }

    companion object {
        fun newInstance(detailId: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("recipeId", detailId)
            fragment.arguments = args
            return fragment
        }
    }

}