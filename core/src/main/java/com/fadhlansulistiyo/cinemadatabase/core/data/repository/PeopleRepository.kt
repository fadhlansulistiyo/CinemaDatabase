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
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IPeopleRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.DATA_IS_EMPTY
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.PeopleMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    override suspend fun getDetailPeople(peopleId: Int): Resource<DetailPeople> {
        return try {
            when (val response = remoteDataSource.getDetailPeople(peopleId)) {
                is ApiResponseResult.Success -> {
                    val people = PeopleMapper.mapDetailPeopleResponseToDomain(response.data)
                    Resource.Success(people)
                }

                is ApiResponseResult.Empty -> {
                    Resource.Error(DATA_IS_EMPTY)
                }

                is ApiResponseResult.Error -> {
                    Resource.Error(response.errorMessage)
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

    override fun getCredits(id: Int): Flow<Resource<List<MultiCreditsMovieTv>>> = flow {
        emit(Resource.Loading())
        try {
            when (val response = remoteDataSource.getCredits(id)) {
                is ApiResponseResult.Success -> {
                    val creditsList = response.data.map {
                        PeopleMapper.mapMultiCreditsResponseToDomain(it)
                    }.filter { it.releaseDate.isNotEmpty() }
                        .sortedByDescending { it.releaseDate }

                    emit(Resource.Success(creditsList))
                }

                is ApiResponseResult.Empty -> {
                    emit(Resource.Error(DATA_IS_EMPTY))
                }

                is ApiResponseResult.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}