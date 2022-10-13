package com.elizav.fooddelivery.data

import com.elizav.fooddelivery.data.api.MealsApi
import com.elizav.fooddelivery.data.mappers.MealMapper.toDomain
import com.elizav.fooddelivery.di.qualifiers.IoDispatcher
import com.elizav.fooddelivery.domain.MealsRepository
import com.elizav.fooddelivery.domain.model.Meal
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MealsRepositoryImpl @Inject constructor(
    private val api: MealsApi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : MealsRepository {

    override suspend fun getMealsForQuery(from: Int, size: Int, query: String): Result<List<Meal>> =
        withContext(coroutineDispatcher) {
            try {
                val response = api.getMeals(from = from, size = size, query = query)
                response.body()?.meals?.map { it.toDomain() }
                    ?.let { Result.success(it) }
                    ?: Result.failure(Error(response.message()))
            } catch (ex: Exception) {
                Result.failure(Error(ex.message))
            }
        }
}