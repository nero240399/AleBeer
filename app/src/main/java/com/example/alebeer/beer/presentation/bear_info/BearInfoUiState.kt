package com.example.alebeer.beer.presentation.bearinfo

import com.example.alebeer.beer.data.local.entity.Beer

data class BearInfoUiState(
    val listBeer: List<Beer> = emptyList(),
    val isLoading: Boolean = false
)
