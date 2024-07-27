package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.datasource

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.TvResponse
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

}
