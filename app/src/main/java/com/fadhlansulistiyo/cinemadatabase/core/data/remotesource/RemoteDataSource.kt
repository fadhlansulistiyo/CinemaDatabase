package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getNowPlaying(): Flow<ApiResponseResult<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getNowPlaying(API_KEY)
                val data = response.results
                if (data.isNotEmpty()) {
                    emit(ApiResponseResult.Success(response.results))
                } else {
                    emit(ApiResponseResult.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponseResult.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}