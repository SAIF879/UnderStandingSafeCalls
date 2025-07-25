package com.example.understandingsafecalls.domain.util

// domain/util/Result.kt
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()

}
