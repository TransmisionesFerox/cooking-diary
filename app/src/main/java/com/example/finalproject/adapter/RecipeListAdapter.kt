package com.example.finalproject.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.EatItemBinding
import com.example.finalproject.model.entity.Recipe

class RecipeListAdapter(private var onItemClickListener: OnItemClickListener? = null) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onRecipeClicked(recipe: Recipe)
    }

    private var items: List<Recipe> = listOf()

    fun updateItems(newItems: List<Recipe>) {
        val diffCallback = RecipeDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: EatItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding) {
                val summary = Html.fromHtml(recipe.summary, Html.FROM_HTML_MODE_LEGACY).toString()
                eatTitle.text = recipe.title
                eatDescr.text = if (summary.length <= 150) summary else "${summary.substring(0, 150)}..."
                Glide.with(context)
                    .load(recipe.image)
                    .into(eatImage)
                itemView.setOnClickListener { onItemClickListener?.onRecipeClicked(recipe) }
            }
        }
    }

    class RecipeDiffCallback(private val oldList: List<Recipe>, private val newList: List<Recipe>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].title == newList[newItemPosition].title
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
    }
}
