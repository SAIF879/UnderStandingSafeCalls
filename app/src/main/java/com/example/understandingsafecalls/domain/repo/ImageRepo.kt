package com.example.understandingsafecalls.domain.repo

import com.example.understandingsafecalls.data.remote.dto.PhotoResponse
import com.example.understandingsafecalls.domain.util.Result


interface ImageRepo{
    suspend fun getAllImages() : Result<PhotoResponse>
}