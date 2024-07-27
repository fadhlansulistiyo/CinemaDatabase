package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.datasource

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRemoteDataSource {

    protected suspend fun <T> handleApiCall(apiCall: suspend () -> T): ApiResponseResult<T> {
        return try {
            val response = apiCall()
            ApiResponseResult.Success(response)
        } catch (e: Exception) {
            ApiResponseResult.Error(e.toString())
        }
    }

    protected fun <T> flowApiCall(apiCall: suspend () -> T): Flow<ApiResponseResult<T>> {
        return flow {
            emit(handleApiCall(apiCall))
        }.flowOn(Dispatchers.IO)
    }

    class EmptyDataException : Exception("No data available")
}
