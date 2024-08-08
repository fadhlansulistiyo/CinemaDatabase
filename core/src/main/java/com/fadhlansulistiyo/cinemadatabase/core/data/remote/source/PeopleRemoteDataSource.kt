package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiCreditsMovieTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.PeopleResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.BaseRemoteDataSource() {

    fun getTrendingPeople(): Flow<ApiResponseResult<List<PeopleResponse>>> {
        return flowApiCall {
            val response = apiService.getTrendingPeople()
            response.results.ifEmpty { throw com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.BaseRemoteDataSource.EmptyDataException() }
        }
    }

    suspend fun getDetailPeople(peopleId: Int): ApiResponseResult<DetailPeopleResponse> {
        return handleApiCall {
            apiService.getDetailPeople(peopleId)
        }
    }

    suspend fun getCredits(id: Int): ApiResponseResult<List<MultiCreditsMovieTvResponse>> {
        return handleApiCall {
            apiService.getCredits(id).cast
        }
    }
}
