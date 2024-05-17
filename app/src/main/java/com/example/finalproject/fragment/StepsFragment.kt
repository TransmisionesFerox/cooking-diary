package com.example.finalproject.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import StepListAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.ViewModel.StepsViewModel
import com.example.finalproject.model.entity.RecipeStep
import com.example.finalproject.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class StepsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StepListAdapter
//    private val viewModel: StepsViewModel by inject()
//    private val viewModel: StepsViewModel by viewModels()
    private val recipeRepository: RecipeRepository by inject() // Предполагается, что у вас уже настроен Koin для внедрения зависимостей

    private val viewModel: StepsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(StepsViewModel::class.java)) {
                    return StepsViewModel(recipeRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frgment_steps, container, false)

        recyclerView = view.findViewById(R.id.steps_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StepListAdapter()
        recyclerView.adapter = adapter

        val recipeId = arguments?.getString("detailId")
        recipeId?.let {
            viewModel.loadSteps(it)
        }

        viewModel.steps.observe(viewLifecycleOwner, Observer { steps ->
            val mutableSteps: MutableList<RecipeStep> = steps.map { it.steps }.flatten().toMutableList()
            adapter.submitList(mutableSteps)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            println("Error: $errorMessage")
        })

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