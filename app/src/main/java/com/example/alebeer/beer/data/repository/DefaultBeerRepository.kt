package com.example.alebeer.beer.data.repository

import com.example.alebeer.beer.data.local.BeerDao
import com.example.alebeer.beer.data.local.entity.BeerEntity
import com.example.alebeer.beer.data.mapper.toBeerEntity
import com.example.alebeer.beer.data.remote.ApiService
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.beer.domain.repository.BeerRepository
import com.example.alebeer.util.Result
import com.example.alebeer.util.runCatching
import kotlinx.coroutines.flow.Flow

class DefaultBeerRepository(
    private val apiService: ApiService,
    private val dao: BeerDao
) : BeerRepository {

    override suspend fun fetchBeerInfo(): Result<List<BeerDto>> =
        runCatching { apiService.getListBeer().data }

    override fun getBeerStream(): Flow<List<BeerEntity>> {
        return dao.observeListBeer()
    }

    override suspend fun saveBeerInfo(beer: Beer, note: String) {
        dao.insert(beer.toBeerEntity(note))
    }

    override suspend fun deleteBeerInfo(beer: Beer) {
        dao.delete(beer.toBeerEntity())
    }

    override suspend fun updateBeerInfo(beer: Beer, note: String) {
        dao.insert(beer.toBeerEntity(note))
    }
}
