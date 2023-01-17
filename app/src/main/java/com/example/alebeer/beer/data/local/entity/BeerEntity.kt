package com.example.alebeer.beer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer")
data class BeerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: String,
    val note: String
)
