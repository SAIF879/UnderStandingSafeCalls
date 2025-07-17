package com.example.understandingsafecalls.domain.repo

import com.example.understandingsafecalls.data.remote.dto.PhotoResponse
import retrofit2.Response


interface ImageRepo{
    suspend fun getAllImages() : Response<PhotoResponse>
}