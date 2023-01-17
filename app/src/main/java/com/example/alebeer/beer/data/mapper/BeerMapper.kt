package com.example.alebeer.beer.data.mapper

import com.example.alebeer.beer.data.local.entity.BeerEntity
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.util.InternalStoragePhoto

fun Beer.toBeerEntity(note: String): BeerEntity = BeerEntity(
    id = this.id,
    name = this.name,
    price = this.price,
    note = note
)

fun Beer.toBeerEntity(): BeerEntity = BeerEntity(
    id = this.id,
    name = this.name,
    price = this.price,
    note = this.note
)

fun BeerDto.toBeer(): Beer = Beer(
    id = this.id,
    name = this.name,
    price = this.price,
    imageUrl = this.imageUrl
)

fun BeerEntity.toBeer(listImage: List<InternalStoragePhoto>) = Beer(
    id = this.id,
    name = this.name,
    price = this.price,
    note = this.note,
    bitmap = listImage.find { it.name == "$name.png" }?.bmp
)
