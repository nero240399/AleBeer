package com.example.alebeer.beer.data.di

import android.content.Context
import com.example.alebeer.beer.data.local.BeerDatabase
import com.example.alebeer.beer.data.remote.ApiService
import com.example.alebeer.beer.data.repository.DefaultBeerRepository
import com.example.alebeer.beer.domain.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BeerRepositoryModule {

    @Provides
    fun provideBeerRepository(
        apiService: ApiService,
        database: BeerDatabase,
        @ApplicationContext context: Context
    ): BeerRepository =
        DefaultBeerRepository(apiService, database.beerDao, context)
}
