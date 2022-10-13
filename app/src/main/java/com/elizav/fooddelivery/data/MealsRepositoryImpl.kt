package com.elizav.fooddelivery.data

import com.elizav.fooddelivery.data.api.MealsApi
import com.elizav.fooddelivery.data.mappers.MealMapper.toDomain
import com.elizav.fooddelivery.di.coroutines.qualifiers.IoDispatcher
import com.elizav.fooddelivery.domain.MealsRepository
import com.elizav.fooddelivery.domain.model.Area
import com.elizav.fooddelivery.domain.model.Category
import com.elizav.fooddelivery.domain.model.Meal
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MealsRepositoryImpl @Inject constructor(
    private val api: MealsApi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : MealsRepository {

    override suspend fun getMealById(id: String): Result<Meal> =
        withContext(coroutineDispatcher) {
            try {
                val response = api.getMealById(id)
                response.body()?.firstOrNull()?.let { Result.success(it.toDomain()) }
                    ?: Result.failure(Error(response.message()))
            } catch (ex: Exception) {
                Result.failure(Error(ex.message))
            }
        }

    override suspend fun getMealsForArea(area: Area): Result<List<Meal>> =
        withContext(coroutineDispatcher) {
            try {
                val response = api.getMealsForArea(area)
                response.body()?.let { Result.success(it.map { meal -> meal.toDomain() }) }
                    ?: Result.failure(Error(response.message()))
            } catch (ex: Exception) {
                Result.failure(Error(ex.message))
            }
        }

    override suspend fun getMealsForCategory(category: Category): Result<List<Meal>> =
        withContext(coroutineDispatcher) {
            try {
                val response = api.getMealsForCategory(category)
                response.body()?.let { Result.success(it.map { meal -> meal.toDomain() }) }
                    ?: Result.failure(Error(response.message()))
            } catch (ex: Exception) {
                Result.failure(Error(ex.message))
            }
        }
}