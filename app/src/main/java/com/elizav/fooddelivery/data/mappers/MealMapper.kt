package com.elizav.fooddelivery.data.mappers

import com.elizav.fooddelivery.data.model.Meal
import com.elizav.fooddelivery.data.model.MealFull
import com.elizav.fooddelivery.domain.model.Category
import com.elizav.fooddelivery.domain.model.FullInfo
import kotlin.reflect.full.declaredMemberProperties
import com.elizav.fooddelivery.domain.model.Meal as MealDomain

object MealMapper {
    private val ingredientsRegex = Regex(".*Ingredient.*")

    fun Meal.toDomain() = MealDomain(
        id = id, name = name, thumbLink = thumbLink
    )

    fun MealFull.toDomain() = MealDomain(
        id = id,
        name = name,
        thumbLink = thumbLink,
        info = FullInfo(
            ingredients = mapIngredients(this),
            category = Category.values().first{it.text == category}
        )
    )

    private fun mapIngredients(mealFull: MealFull) = MealFull::class.java.kotlin
        .declaredMemberProperties.filter { ingredientsRegex.matches(it.name) }
        .mapNotNull { it.getter.call(mealFull)?.toString() }
}