package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import android.util.Log
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) : IMovieRepository {

    override fun getNowPlaying(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
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

    override suspend fun getDetailMovie(movieId: Int): Resource<DetailMovie> {
        return try {
            when (val response = remoteDataSource.getDetailMovie(movieId)) {
                is ApiResponseResult.Success -> {
                    val movie = MovieMapper.mapDetailMovieResponseToDomain(response.data)
                    Log.d("MovieRepository", "Detail Movie: $movie")
                    Resource.Success(movie)
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

    override fun getCast(movieId: Int): Flow<Resource<List<MovieCast>>> = flow {
        emit(Resource.Loading())
        try {
            when (val response = remoteDataSource.getCast(movieId)) {
                is ApiResponseResult.Success -> {
                    val castList = response.data.map {
                        MovieMapper.mapCastResponseToDomain(it)
                    }
                    Log.d("MovieRepository", "Cast List: $castList")
                    emit(Resource.Success(castList))
                }
                is ApiResponseResult.Empty -> {
                    Log.e("MovieRepository", "Error: $response")
                    emit(Resource.Error(DATA_IS_EMPTY))
                }
                is ApiResponseResult.Error -> {
                    Log.e("MovieRepository", "Error: ${response.errorMessage}")
                    emit(Resource.Error(response.errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }
}