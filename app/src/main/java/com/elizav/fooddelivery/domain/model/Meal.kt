package com.elizav.fooddelivery.domain.model

data class Meal(
    val id: String,
    val name: String,
    val thumbLink: String,
    val info: FullInfo? = null,
)