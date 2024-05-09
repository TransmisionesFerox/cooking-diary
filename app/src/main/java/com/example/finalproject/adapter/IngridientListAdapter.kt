package com.example.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.entity.Ingredient
import kotlin.math.log

class IngridientListAdapter(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngridientListAdapter.IngredientViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.ingridient_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]

        holder.ingredientName.text = ingredient.name
        val formattedQuantity = String.format("%.2f", ingredient.amount) + " "
        holder.ingredientQuantity.text = formattedQuantity
        holder.ingredientMeasure.text = ingredient.measures.us.unitShort
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    class IngredientViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName: TextView = itemView.findViewById(R.id.ingridient_name)
        val ingredientQuantity: TextView = itemView.findViewById(R.id.ingridient_amount)
        val ingredientMeasure: TextView = itemView.findViewById(R.id.ingridient_measure)
    }


}