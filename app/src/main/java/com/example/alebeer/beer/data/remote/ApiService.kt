package com.example.alebeer.beer.data.remote

import com.example.alebeer.beer.data.remote.dto.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api-testing/sample-data")
    suspend fun getListBeer(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): BeerResponse

    companion object {
        const val UTHUS_BASE_URL = "https://apps.uthus.vn/api/"
    }
}
