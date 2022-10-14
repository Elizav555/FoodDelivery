package com.elizav.fooddelivery.ui.menu.list.meal

import androidx.recyclerview.widget.DiffUtil
import com.elizav.fooddelivery.domain.model.Meal

class MealDiffUtilCallback : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(
        oldItem: Meal,
        newItem: Meal
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Meal,
        newItem: Meal
    ): Boolean = oldItem == newItem
}