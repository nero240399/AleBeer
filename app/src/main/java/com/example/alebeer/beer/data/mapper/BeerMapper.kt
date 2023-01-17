package com.example.alebeer.beer.data.mapper

import com.example.alebeer.beer.data.local.entity.BeerEntity
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.model.Beer

fun Beer.toBeerEntity(note: String): BeerEntity = BeerEntity(
    id = this.id,
    name = this.name,
    price = this.price,
    note = note
)

fun BeerDto.toBeer(): Beer = Beer(
    id = this.id,
    name = this.name,
    price = this.price,
    imageUrl = this.imageUrl
)
