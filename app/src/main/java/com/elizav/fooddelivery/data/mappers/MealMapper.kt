package com.elizav.fooddelivery.data.mappers

import com.elizav.fooddelivery.data.model.Component
import com.elizav.fooddelivery.data.model.Meal
import com.elizav.fooddelivery.data.model.Section
import com.elizav.fooddelivery.domain.model.Meal as MealDomain

object MealMapper {
    fun Meal.toDomain() = MealDomain(
        id = idMeal,
        name = strMeal,
        thumbLink = strMealThumb,
        description = description,
        ingredients = mapIngredients(sections)
    )

    private fun mapIngredients(sections: List<Section>): List<String> {
        val allComponents = mutableListOf<Component>()
        sections.forEach { allComponents.addAll(it.components) }
        return allComponents.map { it.ingredient.name }
    }
}