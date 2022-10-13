package com.elizav.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val count: Int,
    val meals: List<Meal>
)