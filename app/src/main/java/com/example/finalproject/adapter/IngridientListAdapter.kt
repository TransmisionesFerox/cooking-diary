package com.example.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.entity.Ingredient

class IngridientListAdapter : ListAdapter<Ingredient, IngridientListAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.ingridient_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)

        holder.bind(ingredient)
    }

    class IngredientViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientName: TextView = itemView.findViewById(R.id.ingridient_name)
        private val ingredientQuantity: TextView = itemView.findViewById(R.id.ingridient_amount)
        private val ingredientMeasure: TextView = itemView.findViewById(R.id.ingridient_measure)

        fun bind(ingredient: Ingredient) {
            ingredientName.text = ingredient.name
            val formattedQuantity = String.format("%.2f", ingredient.amount) + " "
            ingredientQuantity.text = formattedQuantity
            ingredientMeasure.text = ingredient.measures.us.unitShort
        }
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
}
