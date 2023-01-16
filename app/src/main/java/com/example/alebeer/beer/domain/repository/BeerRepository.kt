package com.example.alebeer.beer.domain.repository

import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.util.Result

interface BeerRepository {
    suspend fun fetchBeerInfo(): Result<List<BeerDto>>
}
