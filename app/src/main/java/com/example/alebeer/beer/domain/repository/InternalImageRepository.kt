package com.example.alebeer.beer.domain.repository

import android.graphics.Bitmap
import com.example.alebeer.util.InternalStoragePhoto

interface InternalImageRepository {
    fun deleteImage(filename: String): Boolean
    fun saveImage(filename: String, bmp: Bitmap): Boolean
    suspend fun loadImages(): List<InternalStoragePhoto>
}
