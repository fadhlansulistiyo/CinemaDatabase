package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.datasource

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.PeopleResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseRemoteDataSource() {

    fun getTrendingPeople(): Flow<ApiResponseResult<List<PeopleResponse>>> {
        return flowApiCall {
            val response = apiService.getTrendingPeople()
            response.results.ifEmpty { throw EmptyDataException() }
        }
    }
}
