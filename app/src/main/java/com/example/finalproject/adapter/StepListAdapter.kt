import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.entity.RecipeStep

class StepListAdapter : ListAdapter<RecipeStep, StepListAdapter.StepViewHolder>(StepDiffCallback()) {
    class StepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val stepTextView: TextView = view.findViewById(R.id.step_number)
        private val imagesRecyclerViewEquipment: RecyclerView = view.findViewById(R.id.equipment_images)
        private val imagesRecyclerViewIngridients: RecyclerView = view.findViewById(R.id.ingridients_images)
        private val equipmentTitle: TextView = view.findViewById(R.id.equipment_title)
        private val IngridientsTitle: TextView = view.findViewById(R.id.ingridients_title)
        private val StepDescr: TextView = view.findViewById(R.id.step_descr)


        fun bind(step: RecipeStep) {
            stepTextView.text = "Step ${step.number}:"

            if (step.equipment.size != 0){
                equipmentTitle.text = "Equipment:"
                imagesRecyclerViewEquipment.layoutManager = LinearLayoutManager(imagesRecyclerViewEquipment.context, LinearLayoutManager.HORIZONTAL, false)
                val equipmentAdapter = ImagesAdapter()
                imagesRecyclerViewEquipment.adapter = equipmentAdapter
                equipmentAdapter.submitList(step.equipment.map { it.image })
            }

            if (step.ingredients.size != 0){
                IngridientsTitle.text = "Ingridients:"
                imagesRecyclerViewIngridients.layoutManager = LinearLayoutManager(imagesRecyclerViewIngridients.context, LinearLayoutManager.HORIZONTAL, false)
                val ingredientsAdapter = ImagesAdapter()
                imagesRecyclerViewIngridients.adapter = ingredientsAdapter
                ingredientsAdapter.submitList(step.ingredients.map { it.image })
            }

            StepDescr.text = step.step
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.step_item, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val currentStep = getItem(position)
        holder.bind(currentStep)
    }
    class StepDiffCallback : DiffUtil.ItemCallback<RecipeStep>() {
        override fun areItemsTheSame(oldItem: RecipeStep, newItem: RecipeStep): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: RecipeStep, newItem: RecipeStep): Boolean {
            return oldItem == newItem
        }
    }
}

