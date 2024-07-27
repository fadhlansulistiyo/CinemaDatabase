package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.NetworkBoundResource
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.LocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.datasource.MovieRemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.AppExecutors
import com.fadhlansulistiyo.cinemadatabase.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getNowPlaying(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<MovieResponse>>> {
                return remoteDataSource.getNowPlaying()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
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
                    val movie = DataMapper.mapDetailMovieResponseToDomain(response.data)
                    Resource.Success(movie)
                }
                is ApiResponseResult.Empty -> Resource.Error("No Data")
                is ApiResponseResult.Error -> Resource.Error(response.errorMessage)
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }

    override fun getBookmarkedMovie(): Flow<List<Movie>> {
        return localDataSource.getBookmarkedMovie().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override fun setBookmarkedMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setBookmarkedMovie(movieEntity, state) }
    }
}