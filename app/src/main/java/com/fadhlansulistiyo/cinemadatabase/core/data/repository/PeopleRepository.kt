package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import android.util.Log
import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.local.PeopleLocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.PeopleRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IPeopleRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.PeopleMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
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
                    Log.d("PeopleRepository", "PeopleRepository: getDetailPeople: ${response.data}")
                    Resource.Success(people)
                }
                is ApiResponseResult.Empty -> {
                    Log.d("PeopleRepository", "PeopleRepository: No data")
                    Resource.Error("No Data")
                }
                is ApiResponseResult.Error -> {
                    Log.d("PeopleRepository", "PeopleRepository: ${response.errorMessage}")
                    Resource.Error(response.errorMessage)
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }
}