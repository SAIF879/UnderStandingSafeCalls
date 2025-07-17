package com.example.understandingsafecalls.data.repo


import com.example.understandingsafecalls.data.remote.api.ApiService
import com.example.understandingsafecalls.data.remote.dto.PhotoResponse
import com.example.understandingsafecalls.domain.repo.ImageRepo
import retrofit2.Response

class ImageRepoImpl(val apiService: ApiService) : ImageRepo{

    override suspend fun getAllImages(): Response<PhotoResponse> {
       val response = apiService.getAllImages()
        return response
    }

}