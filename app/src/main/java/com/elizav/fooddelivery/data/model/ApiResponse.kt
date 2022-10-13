package com.elizav.fooddelivery.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerialName("count")val count: Int,
    @SerialName("results")val results: List<Meal> = emptyList()
)