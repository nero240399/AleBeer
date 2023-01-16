package com.example.alebeer.beer.presentation.bearinfo

import com.example.alebeer.beer.data.remote.dto.BeerDto

data class BearInfoUiState(
    val listBeer: List<BeerDto> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: String = ""
)
