package com.example.alebeer.beer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alebeer.beer.data.local.entity.BeerEntity

@Database(
    entities = [BeerEntity::class],
    version = 1
)
abstract class BeerDatabase : RoomDatabase() {

    abstract val beerDao: BeerDao
}
