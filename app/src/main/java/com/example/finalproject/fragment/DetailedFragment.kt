package com.example.finalproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.model.network.createDetailApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailed, container, false)
        val detailId = arguments?.getString("recipeId")

        val service = createDetailApiService()

        if (detailId != null) {
            service.getRecipeById(detailId).enqueue(object : Callback<RecipeDetail> {
                override fun onResponse(call: Call<RecipeDetail>, response: Response<RecipeDetail>) {
                    if (response.isSuccessful) {
                        val recipeDetail = response.body()
                        if (recipeDetail != null) {
                            view.findViewById<TextView>(R.id.detail_title).text = recipeDetail.title
                            context?.let {
                                Glide.with(it)
                                    .load(recipeDetail.image)
                                    .into(view.findViewById<ImageView>(R.id.detail_image))
                            }
                            view.findViewById<TextView>(R.id.detail_time).text = recipeDetail.readyInMinutes.toString()
                        }
                    } else {
                        println("Response was not successful")
                    }
                }

                override fun onFailure(call: Call<RecipeDetail>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }



        return view
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