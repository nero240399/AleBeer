package com.example.alebeer.beer.data.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
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
import java.io.IOException

class DefaultBeerRepository(
    private val apiService: ApiService,
    private val dao: BeerDao,
    private val context: Context
) : BeerRepository {

    override suspend fun fetchBeerInfo(): Result<List<BeerDto>> =
        runCatching { apiService.getListBeer().data }

    override fun getBeerStream(): Flow<List<BeerEntity>> {
        return dao.observeListBeer()
    }

    override suspend fun saveBeerInfo(beer: Beer, bitmap: Bitmap, note: String): Boolean {
        dao.insert(beer.toBeerEntity(note))
        return savePhotoToInternalStorage(beer.name, bitmap)
    }

    private fun savePhotoToInternalStorage(filename: String, bitmap: Bitmap): Boolean {
        return try {
            context.openFileOutput("$filename.jpg", AppCompatActivity.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}
