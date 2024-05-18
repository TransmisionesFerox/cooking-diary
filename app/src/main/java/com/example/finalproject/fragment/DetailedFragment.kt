package com.example.finalproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.ViewModel.DetailViewModel
import com.example.finalproject.adapter.IngridientListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private lateinit var ingredientsList: RecyclerView
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed, container, false)
        val detailId = arguments?.getString("recipeId")
        ingredientsList = view.findViewById(R.id.ingridients)
        ingredientsList.layoutManager = LinearLayoutManager(context)

        detailId?.let { id ->
            viewModel.loadRecipeDetail(id)
            observeRecipeDetail(view)
        }

        val button: Button = view.findViewById(R.id.button_cook)

        button.setOnClickListener {
            detailId?.let { id ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, StepsFragment.newInstance(id))
                    .addToBackStack(null)
                    .commit()
            }
        }
        return view
    }

    private fun observeRecipeDetail(view: View) {
        viewModel.recipeDetail.observe(viewLifecycleOwner, Observer { recipeDetail ->
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
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            println("Error: $errorMessage")
        })
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
