package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.NO_DATA_AVAILABLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

open class BaseRemoteDataSource {

    protected suspend fun <T> handleApiCall(apiCall: suspend () -> T): ApiResponseResult<T> {
        return try {
            val response = apiCall()
            ApiResponseResult.Success(response)
        } catch (e: Exception) {
            when (e) {
                is IOException -> ApiResponseResult.Error("No internet connection")
                is HttpException -> ApiResponseResult.Error("Network error: ${e.message}")
                else -> ApiResponseResult.Error(e.toString())
            }
        }
    }

    protected fun <T> flowApiCall(apiCall: suspend () -> T): Flow<ApiResponseResult<T>> {
        return flow {
            emit(handleApiCall(apiCall))
        }.flowOn(Dispatchers.IO)
    }

    class EmptyDataException : Exception(NO_DATA_AVAILABLE)
}
