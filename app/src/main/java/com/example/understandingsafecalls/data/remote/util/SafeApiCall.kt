package com.example.core.network

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

inline fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline apiCall: suspend () -> Response<T>,
    crossinline errorMapper: (errorBody: String?, errorCode: Int) -> String = ErrorParser::parse
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    val response = apiCall()
    if (response.isSuccessful) {
        response.body()?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Error("Response body is null", ErrorCodes.NULL_BODY))
    } else {
        val errorBody = response.errorBody()?.string()
        Log.e("SafeApiCall", "HTTP ${response.code()} - $errorBody")
        val errorMessage = errorMapper(errorBody, response.code())
        emit(Resource.Error(errorMessage, response.code()))
    }

}.catch { e ->
    when (e) {
        is CancellationException -> {
            Log.d("SafeApiCall", "Cancelled: ${e.message}")
            throw e
        }
        is IOException -> {
            emit(Resource.Error("Couldn't reach server. Check your internet connection.", ErrorCodes.NETWORK_ERROR, true))
        }
        else -> {
            emit(Resource.Error(e.message ?: "An unexpected error occurred", ErrorCodes.UNKNOWN))
        }
    }
}.flowOn(dispatcher)

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val message: String?,
        val code: Int = ErrorCodes.UNKNOWN,
        val isNetworkError: Boolean = false
    ) : Resource<Nothing>()
}

object ErrorCodes {
    const val UNKNOWN = -1
    const val NETWORK_ERROR = -2
    const val PARSING_ERROR = -3
    const val NULL_BODY = -4
}
object ErrorParser {
    fun parse(errorBody: String?, errorCode: Int): String {
        return errorBody?.let {
            try {
                val errorResponse = Gson().fromJson(it, ErrorResponseDto::class.java)
                errorResponse.message
                    ?: errorResponse.error
                    ?: "Unknown error occurred (Code: $errorCode)"
            } catch (e: Exception) {
                "Error parsing response (Code: $errorCode)"
            }
        } ?: "Unknown error occurred (Code: $errorCode)"
    }
}

data class ErrorResponseDto(
    val message: String? = null,
    val error: String? = null,
    val status: String? = null,
    val timestamp: String? = null
)
