package com.example.alebeer.beer.data.repository

import com.example.alebeer.beer.data.remote.ApiService
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.repository.BeerRepository
import com.example.alebeer.util.Result
import com.example.alebeer.util.runCatching

class DefaultBeerRepository(private val apiService: ApiService) : BeerRepository {
    override suspend fun fetchBeerInfo(): Result<List<BeerDto>> =
        runCatching { apiService.getListBeer().data }
}
