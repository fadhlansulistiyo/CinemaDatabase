package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.local.PeopleLocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.PeopleRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.paging.PopularPeoplePagingSource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeopleWithCredits
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IPeopleRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.UNKNOWN_ERROR
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.PeopleMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val apiService: ApiService,
    private val localDataSource: PeopleLocalDataSource,
    private val remoteDataSource: PeopleRemoteDataSource
) : IPeopleRepository {

    override fun getTrendingPeople(): Flow<Resource<List<People>>> =
        object : NetworkBoundResource<List<People>, List<PeopleResponse>>() {
            override fun loadFromDB(): Flow<List<People>> {
                return localDataSource.getAllPeople().map {
                    PeopleMapper.mapPeopleEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<PeopleResponse>>> {
                return remoteDataSource.getTrendingPeople()
            }

            override suspend fun saveCallResult(data: List<PeopleResponse>) {
                val peopleList = PeopleMapper.mapPeopleResponsesToEntities(data)
                localDataSource.insertPeople(peopleList)
            }

            override fun shouldFetch(data: List<People>?): Boolean {
                return data.isNullOrEmpty()
            }
        }.asFlow()

    override suspend fun getDetailPeople(peopleId: Int): Resource<DetailPeopleWithCredits> {
        return try {
            val (detailResponse, creditsResponse) =
                remoteDataSource.getDetailPeopleWithCredits(peopleId)

            when {
                detailResponse is ApiResponseResult.Success && creditsResponse is ApiResponseResult.Success -> {
                    val detail = PeopleMapper.mapDetailPeopleResponseToDomain(detailResponse.data)
                    val credits = creditsResponse.data.map {
                        PeopleMapper.mapMultiCreditsResponseToDomain(it)
                    }.filter { it.releaseDate.isNotEmpty() }
                        .sortedByDescending { it.releaseDate }
                    Resource.Success(DetailPeopleWithCredits(detail, credits))
                }

                detailResponse is ApiResponseResult.Error -> {
                    Resource.Error(detailResponse.errorMessage)
                }

                creditsResponse is ApiResponseResult.Error -> {
                    Resource.Error(creditsResponse.errorMessage)
                }

                else -> {
                    Resource.Error(UNKNOWN_ERROR)
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }

    }

    override fun getPopularPeople(): Flow<PagingData<PopularPeople>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularPeoplePagingSource(apiService) }
        ).flow
    }
}