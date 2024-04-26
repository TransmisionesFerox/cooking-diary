package com.example.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.EatItemBinding
import com.example.finalproject.model.entity.Recipe

class RecipeListAdapter(private val onItemClickListener: OnItemClickListener? = null) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
        interface OnItemClickListener{
            fun onRecipeClicked(recipe: Recipe)
        }

        private val items: ArrayList<Recipe> = arrayListOf()

        fun updateItems(newItems: List<Recipe>) {
            val diffCallback = RecipeDiffCallback(this.items, newItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.items.clear()
            this.items.addAll(newItems)
            diffResult.dispatchUpdatesTo(this)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                EatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), parent.context
            )
        }

        override fun getItemCount(): Int {
            return items.size
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setClickable(true)
        holder.itemView.isClickable = true
        holder.itemView.setOnClickListener {
            onItemClickListener?.onRecipeClicked(items[position])
        }
    }

        inner class ViewHolder(
            private val binding: EatItemBinding,
            private val context: Context
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(recipe: Recipe) {
                with(binding) {
                    val summary = convertHtmlToPlainText(recipe.summary);
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

        class RecipeDiffCallback(
            private val oldList: List<Recipe>,
            private val newList: List<Recipe>
        ) : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].title == newList[newItemPosition].title
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

        }

    }

