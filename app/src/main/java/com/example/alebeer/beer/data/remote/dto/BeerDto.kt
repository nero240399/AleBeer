package com.example.alebeer.beer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeerResponse(
    val data: List<BeerDto>
)

@Serializable
data class BeerDto(
    val id: Int,
    val price: String,
    val name: String,
    @SerialName("image") val imageUrl: String
)
