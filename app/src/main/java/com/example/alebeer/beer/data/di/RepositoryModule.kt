package com.example.alebeer.beer.data.di

import com.example.alebeer.beer.data.remote.ApiService
import com.example.alebeer.beer.data.repository.DefaultBeerRepository
import com.example.alebeer.beer.domain.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBeerRepository(apiService: ApiService): BeerRepository =
        DefaultBeerRepository(apiService)
}
