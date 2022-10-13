package com.elizav.fooddelivery.domain

import com.elizav.fooddelivery.domain.model.Meal
import com.elizav.fooddelivery.domain.model.Area
import com.elizav.fooddelivery.domain.model.Category

interface MealsRepository {
    suspend fun getMealById(
        id: String,
    ): Result<Meal>

    suspend fun getMealsForArea(
        area: Area,
    ): Result<List<Meal>>

    suspend fun getMealsForCategory(
        category: Category
    ): Result<List<Meal>>
}