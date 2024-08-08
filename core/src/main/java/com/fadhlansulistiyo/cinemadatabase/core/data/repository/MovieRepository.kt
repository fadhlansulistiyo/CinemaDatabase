package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.local.MovieLocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.MovieRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_IS_EMPTY
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.MovieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) : IMovieRepository {

    override fun getNowPlaying(): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<Movie>>> =
        object : com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    MovieMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<MovieResponse>>> {
                return remoteDataSource.getNowPlaying()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = MovieMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data.isNullOrEmpty()
            }

        }.asFlow()

    override suspend fun getDetailMovie(movieId: Int): com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailMovie> {
        return try {
            when (val response = remoteDataSource.getDetailMovie(movieId)) {
                is ApiResponseResult.Success -> {
                    val movie = MovieMapper.mapDetailMovieResponseToDomain(response.data)
                    com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Success(movie)
                }

                is ApiResponseResult.Empty -> {
                    com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(DATA_IS_EMPTY)
                }

                is ApiResponseResult.Error -> {
                    com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(response.errorMessage)
                }
            }
        } catch (e: Exception) {
            com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(e.toString())
        }
    }

    override fun getCast(movieId: Int): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<MovieCast>>> = flow {
        emit(com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Loading())
        try {
            when (val response = remoteDataSource.getCast(movieId)) {
                is ApiResponseResult.Success -> {
                    val castList = response.data.map {
                        MovieMapper.mapCastResponseToDomain(it)
                    }
                    emit(com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Success(castList))
                }

                is ApiResponseResult.Empty -> {
                    emit(com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(DATA_IS_EMPTY))
                }

                is ApiResponseResult.Error -> {
                    emit(com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(response.errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}