package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.LocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.RemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ITvRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.AppExecutors
import com.fadhlansulistiyo.cinemadatabase.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : ITvRepository {

    override fun getAiringTodayTv(): Flow<Resource<List<Tv>>> =
        object : NetworkBoundResource<List<Tv>, List<TvResponse>>() {
            override fun loadFromDB(): Flow<List<Tv>> {
                return localDataSource.getAllTv().map {
                    DataMapper.mapTvEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<TvResponse>>> {
                return remoteDataSource.getAiringTodayTv()
            }

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val tvList = DataMapper.mapTvResponsesToEntities(data)
                localDataSource.insertTv(tvList)
            }

            override fun shouldFetch(data: List<Tv>?): Boolean {
                return data.isNullOrEmpty()
            }
        }.asFlow()
}