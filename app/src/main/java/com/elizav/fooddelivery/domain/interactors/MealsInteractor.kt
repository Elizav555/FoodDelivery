package com.elizav.fooddelivery.domain.interactors

import com.elizav.fooddelivery.domain.model.Area
import com.elizav.fooddelivery.domain.model.Category
import com.elizav.fooddelivery.domain.model.Meal

interface MealsInteractor {
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