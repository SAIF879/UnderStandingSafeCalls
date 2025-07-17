package com.example.understandingsafecalls.di

import com.example.understandingsafecalls.data.remote.api.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {


    // JSON Config
    single {
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        val contentType = "application/json".toMediaType()
        val json = Json {
            prettyPrint = true // good in eyes , though slightly higher network time
            ignoreUnknownKeys = true // If the incoming JSON contains extra keys that are not present in your Kotlin data class, they will be ignored instead of throwing an exception.
            isLenient = true // Allows parsing relaxed JSON formats: ex -> { 'name': 'Saif', }
        }
        //TODO : ADD APPLICATION INTERCEPTORS
//        val httpClient = OkHttpClient.Builder()
//            .addInterceptor {  }
//            .addInterceptor {  }
        Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
//            .addConverterFactory(GsonConverterFactory.create())  todo(Not Suitable for pure kotlin projects because of reflections and speed)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }
}