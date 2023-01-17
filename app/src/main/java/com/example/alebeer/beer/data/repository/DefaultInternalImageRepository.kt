package com.example.alebeer.beer.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import com.example.alebeer.beer.domain.repository.InternalImageRepository
import com.example.alebeer.util.InternalStoragePhoto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class DefaultInternalImageRepository(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) : InternalImageRepository {

    override fun deleteImage(filename: String): Boolean {
        return try {
            context.deleteFile(filename)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun saveImage(filename: String, bmp: Bitmap): Boolean {
        return try {
            context.openFileOutput("$filename.png", AppCompatActivity.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.PNG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun loadImages(): List<InternalStoragePhoto> {
        return withContext(ioDispatcher) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".png") }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }
}
