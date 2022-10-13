package com.elizav.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val description: String,
    val id: Int,
    val name: String,
    val sections: List<Section>,
    val thumbnail_url: String
)