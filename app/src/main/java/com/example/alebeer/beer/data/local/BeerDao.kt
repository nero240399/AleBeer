package com.example.alebeer.beer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alebeer.beer.data.local.entity.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beerEntity: BeerEntity)

    @Query("SELECT * From beer")
    fun observeListBeer(): Flow<List<BeerEntity>>

    @Update
    suspend fun update(beerEntity: BeerEntity)

    @Delete
    suspend fun delete(beerEntity: BeerEntity)
}
