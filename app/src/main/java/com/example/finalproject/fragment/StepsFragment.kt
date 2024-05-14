package com.example.finalproject.fragment

import StepListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.network.RecipeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class StepsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StepListAdapter

    private val recipeService: RecipeService by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frgment_steps, container, false)

        recyclerView = view.findViewById(R.id.steps_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StepListAdapter()
        recyclerView.adapter = adapter

        loadSteps()

        return view
    }

    private fun loadSteps() {
        val recipeId = arguments?.getString("detailId")

        recipeId?.let {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val steps = withContext(Dispatchers.IO) {
                        recipeService.getStepsById(it)
                    }
                    adapter.setSteps(steps.flatMap { it.steps })
                } catch (e: Exception) {
                    println("Error fetching recipe steps: ${e.message}")
                }
            }
        }
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