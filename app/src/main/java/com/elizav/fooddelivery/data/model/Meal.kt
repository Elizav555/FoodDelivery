package com.elizav.fooddelivery.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    @SerialName("description") val description: String? = "",
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String? = "",
    @SerialName("sections") val sections: List<Section>? = emptyList(),
    @SerialName("thumbnail_url") val thumbnail_url: String? = ""
)