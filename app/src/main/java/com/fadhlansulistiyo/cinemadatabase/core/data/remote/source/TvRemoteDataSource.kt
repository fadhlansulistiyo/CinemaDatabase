package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.TvResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseRemoteDataSource() {

    fun getAiringTodayTv(): Flow<ApiResponseResult<List<TvResponse>>> {
        return flowApiCall {
            val response = apiService.getAiringTodayTv()
            response.results.ifEmpty { throw EmptyDataException() }
        }
    }

    suspend fun getDetailTv(seriesId: Int): ApiResponseResult<DetailTvResponse> {
        return handleApiCall {
            apiService.getDetailTv(seriesId)
        }
    }
}
