package com.example.alebeer.beer.domain.model

data class Beer(
    val id: Int,
    val name: String,
    val price: String,
    val note: String = "",
    val imageUrl: String,
    var isLoading: Boolean = false,
    val isSaved: Boolean = false
)
