package com.elizav.fooddelivery.ui.menu.list

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elizav.fooddelivery.databinding.ItemMealBinding
import com.elizav.fooddelivery.domain.model.Meal

class MealHolder(
    private val binding: ItemMealBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(meal: Meal) {
        with(binding) {
            tvName.text = meal.name
            tvIngredients.text = if (meal.ingredients.isEmpty()) meal.description else
                meal.ingredients.joinToString(separator = ", ", postfix = ".")
            ivThumbnail.load(meal.thumbLink)
        }
    }
}