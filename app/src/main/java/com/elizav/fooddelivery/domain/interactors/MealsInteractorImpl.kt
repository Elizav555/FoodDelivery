package com.elizav.fooddelivery.domain.interactors

import com.elizav.fooddelivery.domain.MealsRepository
import com.elizav.fooddelivery.domain.model.Meal
import javax.inject.Inject

class MealsInteractorImpl @Inject constructor(
    private val mealsRepository: MealsRepository,
) : MealsInteractor {
    override suspend fun getMealsForQuery(from: Int, size: Int, query: String): Result<List<Meal>> =
        mealsRepository.getMealsForQuery(from = from, size = size, query = query)
}