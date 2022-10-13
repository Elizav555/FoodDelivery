package com.elizav.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Component(
    val ingredient: Ingredient,
)