package com.elizav.fooddelivery.domain.interactors

import com.elizav.fooddelivery.domain.MealsRepository
import com.elizav.fooddelivery.domain.model.Area
import com.elizav.fooddelivery.domain.model.Category
import com.elizav.fooddelivery.domain.model.Meal
import javax.inject.Inject

class MealsInteractorImpl @Inject constructor(
    private val mealsRepository: MealsRepository
) : MealsInteractor {
    override suspend fun getMealById(id: String): Result<Meal> = mealsRepository.getMealById(id)

    override suspend fun getMealsForArea(area: Area): Result<List<Meal>> =
        mealsRepository.getMealsForArea(area)

    override suspend fun getMealsForCategory(category: Category): Result<List<Meal>> =
        mealsRepository.getMealsForCategory(category)
}