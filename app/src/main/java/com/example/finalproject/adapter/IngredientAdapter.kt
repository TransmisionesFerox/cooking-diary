package com.example.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.entity.Ingredient

class IngredientAdapter(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.ingredient_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.ingredientName.text = ingredient.name
        val formattedQuantity = String.format("%.2f", ingredient.quantity) + " "  // Format quantity to 2 decimal places
        holder.ingredientQuantity.text = formattedQuantity
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    class IngredientViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName: TextView = itemView.findViewById(R.id.ingredient_name)
        val ingredientQuantity: TextView = itemView.findViewById(R.id.ingredient_quantity)
    }
}