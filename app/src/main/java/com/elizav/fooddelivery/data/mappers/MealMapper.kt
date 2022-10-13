package com.elizav.fooddelivery.data.mappers

import com.elizav.fooddelivery.data.model.Component
import com.elizav.fooddelivery.data.model.Meal
import com.elizav.fooddelivery.data.model.Section
import com.elizav.fooddelivery.domain.model.Meal as MealDomain

object MealMapper {
    fun Meal.toDomain() = MealDomain(
        id = id,
        name = name ?: "",
        thumbLink = thumbnail_url ?: "",
        description = description ?: "",
        ingredients = sections?.let { mapIngredients(it) } ?: emptyList()
    )

    private fun mapIngredients(sections: List<Section>): List<String> {
        val allComponents = mutableListOf<Component>()
        sections.forEach { section ->
            section.components?.let { components ->
                allComponents.addAll(
                    components
                )
            }
        }
        return allComponents.mapNotNull { it.ingredient?.name }
    }
}