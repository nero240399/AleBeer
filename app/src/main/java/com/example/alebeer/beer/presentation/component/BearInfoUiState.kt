package com.example.alebeer.beer.presentation.component

import com.example.alebeer.beer.domain.model.Beer

data class BearInfoUiState(
    val listBeer: List<Beer> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: String = ""
)
