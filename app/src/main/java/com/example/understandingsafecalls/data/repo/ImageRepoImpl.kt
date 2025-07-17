package com.example.understandingsafecalls.data.repo

import com.example.understandingsafecalls.data.remote.api.ApiService
import com.example.understandingsafecalls.data.remote.dto.PhotoResponse
import com.example.understandingsafecalls.domain.repo.ImageRepo
import com.example.understandingsafecalls.domain.util.Result

class ImageRepoImpl(val apiService: ApiService) : ImageRepo{
    override suspend fun getAllImages(): Result<PhotoResponse> = runCatching {
        apiService.getAllImages()
    }.fold (
        onSuccess = { response ->
            when {
                response.isSuccessful && response.body() != null -> {
                    Result.Success(response.body()!!)
                }
                else -> {
                    Result.Error("HTTP ${response.code()}: ${response.message()}")
                }
            }
        },
        onFailure = { e ->
            Result.Error("Network error: ${e.localizedMessage ?: "Unknown error"}")
        }
    )


}