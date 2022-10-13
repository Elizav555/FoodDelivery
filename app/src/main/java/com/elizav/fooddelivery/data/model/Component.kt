package com.elizav.fooddelivery.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Component(
    @SerialName("ingredient") val ingredient: Ingredient?=Ingredient(),
)