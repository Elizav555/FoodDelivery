package com.elizav.fooddelivery.data.api

import com.elizav.fooddelivery.data.model.Meal
import com.elizav.fooddelivery.data.model.MealFull
import com.elizav.fooddelivery.domain.model.Area
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("/api/json/v1/lookup.php?")
    suspend fun getMealById(
        @Query("i") id: String,
    ): Response<List<MealFull>>

    @GET("/api/json/v1/filter.php?")
    suspend fun getMealsForArea(
        @Query("a") area: Area,
    ): Response<List<Meal>>
}