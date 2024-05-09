package com.example.finalproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.adapter.IngridientListAdapter
import com.example.finalproject.model.entity.RecipeDetail
import com.example.finalproject.model.entity.RecipeSteps
import com.example.finalproject.model.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StepsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frgment_steps, container, false)
        val recipeId = arguments?.getString("detailId")
        val service = ApiClient.createApiService

        if (recipeId != null) {
            service.getStepsById(recipeId).enqueue(object : Callback<List<RecipeSteps>> {
                override fun onResponse(call: Call<List<RecipeSteps>>, response: Response<List<RecipeSteps>>) {
                    if (response.isSuccessful) {
                        val recipeSteps = response.body()

                        println(recipeSteps)
//                        view.findViewById<TextView>(R.id.step_number).text = recipeSteps
                    } else {
                        println("Response was not successful")
                    }
                }

                override fun onFailure(call: Call<List<RecipeSteps>>, t: Throwable) {
                    println("Error fetching recipe steps: ${t.message}")
                }
            })
        }

        return view
    }

    companion object {
        fun newInstance(detailId: String?) : StepsFragment{
            val fragment = StepsFragment()
            val args = Bundle()
            args.putString("detailId", detailId)
            fragment.arguments = args
            return fragment
        }
    }

}