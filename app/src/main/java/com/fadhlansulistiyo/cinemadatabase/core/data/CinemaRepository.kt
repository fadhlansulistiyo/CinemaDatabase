package com.fadhlansulistiyo.cinemadatabase.core.data

import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.LocalDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.RemoteDataSource
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.ICinemaRepository
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.utils.AppExecutors
import com.fadhlansulistiyo.cinemadatabase.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : ICinemaRepository {

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