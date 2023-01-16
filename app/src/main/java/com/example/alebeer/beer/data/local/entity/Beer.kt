package com.example.alebeer.beer.data.local.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @DrawableRes val imageRes: Int,
    val name: String,
    val price: Int,
    val note: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
