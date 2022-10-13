package com.elizav.fooddelivery.domain

import com.elizav.fooddelivery.domain.model.Meal

interface MealsRepository {
    suspend fun getMealsForQuery(
        from: Int,
        size: Int,
        query: String,
    ): Result<List<Meal>>
}