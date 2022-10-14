package com.elizav.fooddelivery.ui.menu.list.meal

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.elizav.fooddelivery.databinding.ItemMealBinding
import com.elizav.fooddelivery.domain.model.Meal

class MealHolder(
    private val imageLoader: ImageLoader,
    private val binding: ItemMealBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(meal: Meal) {
        with(binding) {
            tvName.text = meal.name
            tvIngredients.text = if (meal.ingredients.isEmpty()) meal.description else
                meal.ingredients.joinToString(separator = ", ", postfix = ".")

            val request = ImageRequest.Builder(ivThumbnail.context)
                .data(meal.thumbLink)
                .target(ivThumbnail)
                .build()
            imageLoader.enqueue(request)
        }
    }
}