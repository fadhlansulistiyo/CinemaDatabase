package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.local.TvLocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.TvRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ITvRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_IS_EMPTY
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.TvMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepository @Inject constructor(
    private val localDataSource: TvLocalDataSource,
    private val remoteDataSource: TvRemoteDataSource
) : ITvRepository {

    override fun getAiringTodayTv(): Flow<Resource<List<Tv>>> =
        object : NetworkBoundResource<List<Tv>, List<TvResponse>>() {
            override fun loadFromDB(): Flow<List<Tv>> {
                return localDataSource.getAllTv().map {
                    TvMapper.mapTvEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<TvResponse>>> {
                return remoteDataSource.getAiringTodayTv()
            }

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val tvList = TvMapper.mapTvResponsesToEntities(data)
                localDataSource.insertTv(tvList)
            }

            override fun shouldFetch(data: List<Tv>?): Boolean {
                return data.isNullOrEmpty()
            }
        }.asFlow()

    override suspend fun getDetailTv(seriesId: Int): Resource<DetailTv> {
        return try {
            when (val response = remoteDataSource.getDetailTv(seriesId)) {
                is ApiResponseResult.Success -> {
                    val tv = TvMapper.mapDetailTvResponseToDomain(response.data)
                    Resource.Success(tv)
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

    override fun getCast(seriesId: Int): Flow<Resource<List<TvCast>>> = flow {
        emit(Resource.Loading())
        try {
            when (val response = remoteDataSource.getCast(seriesId)) {
                is ApiResponseResult.Success -> {
                    val castList = response.data.map {
                        TvMapper.mapCastResponseToDomain(it)
                    }
                    emit(Resource.Success(castList))
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