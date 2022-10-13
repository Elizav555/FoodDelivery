package com.elizav.fooddelivery.domain.interactors

import com.elizav.fooddelivery.domain.model.Meal

interface MealsInteractor {
    suspend fun getMealsForQuery(
        from: Int = 0,
        size: Int = 25,
        query: String,
    ): Result<List<Meal>>
}