package com.example.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.EatItemBinding
import com.example.finalproject.model.entity.Recipe
import com.example.finalproject.model.interfaces.OnRecipeClickListener

class RecipeListAdapter(private val listener: OnRecipeClickListener) :
    ListAdapter<Recipe, RecipeListAdapter.ViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context, listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: EatItemBinding,
        private val context: Context,
        private val listener: OnRecipeClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onRecipeClicked(getItem(position))
                }
            }
        }

        fun bind(recipe: Recipe) {
            with(binding) {
                val summary = convertHtmlToPlainText(recipe.summary)
                eatTitle.text = recipe.title

                if (summary.length <= 150) {
                    eatDescr.text = summary
                } else {
                    eatDescr.text = summary.substring(0, 150) + "..."
                }
                Glide.with(context)
                    .load(recipe.image)
                    .into(eatImage)
            }
        }
    }

    private fun convertHtmlToPlainText(htmlText: String): String {
        return HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}