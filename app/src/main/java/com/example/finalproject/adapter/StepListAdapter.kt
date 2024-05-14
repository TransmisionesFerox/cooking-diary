import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.entity.RecipeStep

class StepListAdapter : RecyclerView.Adapter<StepListAdapter.StepViewHolder>() {

    private var steps: List<RecipeStep> = emptyList()
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
                val imageUrlsEquipment = step.equipment.map { it.image }
                imagesRecyclerViewEquipment.adapter = ImagesAdapter(imageUrlsEquipment)
            }

            if (step.ingredients.size != 0){
                IngridientsTitle.text = "Ingridients:"
                imagesRecyclerViewIngridients.layoutManager = LinearLayoutManager(imagesRecyclerViewIngridients.context, LinearLayoutManager.HORIZONTAL, false)
                val imageUrlsIngridients = step.ingredients.map { it.image }
                imagesRecyclerViewIngridients.adapter = ImagesAdapter(imageUrlsIngridients)
            }

            StepDescr.text = step.step
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.step_item, parent, false)
        return StepViewHolder(view)
    }

    override fun getItemCount(): Int = steps.size

    fun setSteps(steps: List<RecipeStep>) {
        this.steps = steps
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val currentStep = steps[position]
        holder.bind(currentStep)
    }
}