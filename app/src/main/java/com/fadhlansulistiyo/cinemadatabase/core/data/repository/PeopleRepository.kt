package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.LocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.datasource.PeopleRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IPeopleRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.AppExecutors
import com.fadhlansulistiyo.cinemadatabase.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: PeopleRemoteDataSource
) : IPeopleRepository {

    override fun getTrendingPeople(): Flow<Resource<List<People>>> =
        object : NetworkBoundResource<List<People>, List<PeopleResponse>>() {
            override fun loadFromDB(): Flow<List<People>> {
                return localDataSource.getAllPeople().map {
                    DataMapper.mapPeopleEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<PeopleResponse>>> {
                return remoteDataSource.getTrendingPeople()
            }

            override suspend fun saveCallResult(data: List<PeopleResponse>) {
                val peopleList = DataMapper.mapPeopleResponsesToEntities(data)
                localDataSource.insertPeople(peopleList)
            }

            override fun shouldFetch(data: List<People>?): Boolean {
                return data.isNullOrEmpty()
            }
        }.asFlow()
}