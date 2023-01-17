package com.example.alebeer.beer.presentation.bearinfo

import android.graphics.Bitmap
import com.example.alebeer.beer.domain.model.Beer

sealed class BeerInfoEvent {
    data class OnSaveButton(val beer: Beer, val bitmap: Bitmap, val note: String) : BeerInfoEvent()
}
