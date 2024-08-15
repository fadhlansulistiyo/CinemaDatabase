package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.local.MovieLocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.MovieRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovieWithCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.UNKNOWN_ERROR
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.MovieMapper
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getDetailMovie(movieId: Int): Resource<DetailMovieWithCast> {
        return try {
            val (detailResponse, castResponse) = remoteDataSource.getDetailMovieWithCast(movieId)

            when {
                detailResponse is ApiResponseResult.Success && castResponse is ApiResponseResult.Success -> {
                    val detail = MovieMapper.mapDetailMovieResponseToDomain(detailResponse.data)
                    val cast = castResponse.data.map {
                        MovieMapper.mapCastResponseToDomain(it)
                    }
                    Resource.Success(DetailMovieWithCast(detail, cast))
                }

                detailResponse is ApiResponseResult.Error -> {
                    Resource.Error(detailResponse.errorMessage)
                }

                castResponse is ApiResponseResult.Error -> {
                    Resource.Error(castResponse.errorMessage)
                }

                else -> {
                    Resource.Error(UNKNOWN_ERROR)
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }
}