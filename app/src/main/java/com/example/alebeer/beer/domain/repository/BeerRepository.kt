package com.example.alebeer.beer.domain.repository

import android.graphics.Bitmap
import com.example.alebeer.beer.data.local.entity.BeerEntity
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.util.Result
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun fetchBeerInfo(): Result<List<BeerDto>>

    suspend fun saveBeerInfo(beer: Beer, bitmap: Bitmap, note: String): Boolean

    fun getBeerStream(): Flow<List<BeerEntity>>
}
