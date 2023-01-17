package com.example.alebeer.beer.presentation.favorite

import com.example.alebeer.beer.domain.model.Beer

sealed class FavoriteEvent {
    data class OnDeleteButton(val beer: Beer) : FavoriteEvent()
    data class OnUpdateButton(val beer: Beer, val note: String) : FavoriteEvent()
}
