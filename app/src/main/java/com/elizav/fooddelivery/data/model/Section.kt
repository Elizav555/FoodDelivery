package com.elizav.fooddelivery.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Section(
    val components: List<Component>,
)