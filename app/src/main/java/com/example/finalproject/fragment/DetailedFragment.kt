package com.example.finalproject.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.adapter.IngredientAdapter
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.model.network.createDetailApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    private lateinit var recipeImage: ImageView
    private lateinit var recipeTitle: TextView
    private lateinit var recipeSummary: TextView
    private lateinit var cookingTimeLabel: TextView
    private lateinit var cookingTime: TextView
    private lateinit var ingredientsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed, container, false)

        recipeImage = view.findViewById(R.id.recipe_image)
        recipeTitle = view.findViewById(R.id.recipe_title)
        recipeSummary = view.findViewById(R.id.recipe_summary)
//        cookingTimeLabel = view.findViewById(R.id.cooking_time_label)
        cookingTime = view.findViewById(R.id.cooking_time)
        ingredientsList = view.findViewById(R.id.ingredients_recycler_view)

        val service = createDetailApiService()
        val recipeId = arguments?.getString("recipeId")

        recipeId?.let { id ->
            service.getRecipeById(id).enqueue(object : Callback<RecipeDetail> {
                override fun onResponse(
                    call: Call<RecipeDetail>,
                    response: Response<RecipeDetail>
                ) {
                    if (response.isSuccessful) {
                        val recipeDetail = response.body()
                        recipeDetail?.let { displayRecipeDetails(it) }
                    } else {
                        Log.e("DetailFragment", "Response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<RecipeDetail>, t: Throwable) {
                    Log.e("DetailFragment", "Network request failed", t)
                }
            })
        }

        return view
    }

    private fun displayRecipeDetails(recipeDetail: RecipeDetail) {
        recipeTitle.text = recipeDetail.title
        recipeSummary.text = recipeDetail.summary
        cookingTime.text = "${recipeDetail.readyInMinutes} minutes"
        Glide.with(this).load(recipeDetail.image).into(recipeImage)

        // Set up ingredients list
        ingredientsList.layoutManager = LinearLayoutManager(context)
        val adapter = IngredientAdapter(recipeDetail.ingredients)
        ingredientsList.adapter = adapter
    }

    companion object {
        fun newInstance(recipeId: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply {
                putString("recipeId", recipeId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
