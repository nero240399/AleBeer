package com.example.alebeer.beer.data.di

import android.content.Context
import com.example.alebeer.beer.data.local.BeerDatabase
import com.example.alebeer.beer.data.remote.ApiService
import com.example.alebeer.beer.data.repository.DefaultBeerRepository
import com.example.alebeer.beer.data.repository.DefaultInternalImageRepository
import com.example.alebeer.beer.domain.repository.BeerRepository
import com.example.alebeer.beer.domain.repository.InternalImageRepository
import com.example.alebeer.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BeerRepositoryModule {

    @Provides
    @Singleton
    fun provideBeerRepository(
        apiService: ApiService,
        database: BeerDatabase
    ): BeerRepository = DefaultBeerRepository(apiService, database.beerDao)

    @Provides
    @Singleton
    fun provideInternalImageRepository(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): InternalImageRepository = DefaultInternalImageRepository(context, dispatcher)
}
