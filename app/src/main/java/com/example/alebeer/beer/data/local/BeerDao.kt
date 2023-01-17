package com.example.alebeer.beer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alebeer.beer.data.local.entity.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beerEntity: BeerEntity)

    @Query("SELECT * From beer")
    fun observeListBeer(): Flow<List<BeerEntity>>
}
