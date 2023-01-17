package com.example.alebeer.beer.domain.repository

import com.example.alebeer.beer.data.local.entity.BeerEntity
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.util.Result
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun fetchBeerInfo(): Result<List<BeerDto>>

    suspend fun saveBeerInfo(beer: Beer, note: String)

    fun getBeerStream(): Flow<List<BeerEntity>>

    suspend fun deleteBeerInfo(beer: Beer)

    suspend fun updateBeerInfo(beer: Beer, note: String)
}
