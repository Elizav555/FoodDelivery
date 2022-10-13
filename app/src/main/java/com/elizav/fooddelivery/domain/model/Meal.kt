package com.elizav.fooddelivery.domain.model

data class Meal(
    val id: String,
    val name: String,
    val thumbLink: String,
    val description:String,
    val ingredients: List<String>
)