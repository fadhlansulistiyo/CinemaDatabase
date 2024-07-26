package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.TvEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val cinemaDao: CinemaDao) {

    fun getAllMovie(): Flow<List<MovieEntity>> = cinemaDao.getAllMovie()

    fun getAllTv(): Flow<List<TvEntity>> = cinemaDao.getAllTv()

    fun getAllPeople(): Flow<List<PeopleEntity>> = cinemaDao.getAllPeople()

    fun getBookmarkedMovie(): Flow<List<MovieEntity>> = cinemaDao.getBookmarkedMovie()

    suspend fun insertMovie(movie: List<MovieEntity>) = cinemaDao.insertMovies(movie)

    suspend fun insertTv(tv: List<TvEntity>) = cinemaDao.insertTv(tv)

    suspend fun insertPeople(people: List<PeopleEntity>) = cinemaDao.insertPeople(people)

    fun setBookmarkedMovie(movie: MovieEntity, newState: Boolean) {
        movie.isBookmarked = newState
        cinemaDao.updateBookmarkMovie(movie)
    }

}