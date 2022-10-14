package com.elizav.fooddelivery.ui.menu.list.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import com.elizav.fooddelivery.databinding.ItemMealBinding
import com.elizav.fooddelivery.domain.model.Meal

class MealAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<Meal, MealHolder>(MealDiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealHolder = MealHolder(
        binding = ItemMealBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        imageLoader = imageLoader
    )

    override fun onBindViewHolder(
        holder: MealHolder,
        position: Int
    ) {
        val meal = getItem(position)
        holder.bind(meal)
    }

    override fun submitList(list: List<Meal>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}