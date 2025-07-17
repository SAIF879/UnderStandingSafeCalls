package com.example.understandingsafecalls.data.remote.api

import com.example.understandingsafecalls.BuildConfig
import com.example.understandingsafecalls.data.remote.dto.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("https://pixabay.com/api/")
    suspend fun getAllImages(
        @Query("key") key: String = BuildConfig.API_KEY,
    ) : Response<PhotoResponse>
}