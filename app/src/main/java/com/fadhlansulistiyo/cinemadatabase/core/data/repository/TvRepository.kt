package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import android.util.Log
import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.local.TvLocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.TvRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ITvRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.TvMapper
import kotlinx.coroutines.flow.Flow
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
                    Log.d("TvRepository", "TvRepository: getTv: ${response.data}")
                    Resource.Success(tv)
                }
                is ApiResponseResult.Empty -> {
                    Log.d("TvRepository", "TvRepository: getTv: Empty")
                    Resource.Error("No Data")
                }
                is ApiResponseResult.Error -> {
                    Log.d("TvRepository", "TvRepository: getTv: ${response.errorMessage}")
                    Resource.Error(response.errorMessage)
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }


}