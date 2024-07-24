package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val cinemaDao: CinemaDao) {

    fun getAllMovie(): Flow<List<MovieEntity>> = cinemaDao.getAllMovie()

    fun getBookmarkedMovie(): Flow<List<MovieEntity>> = cinemaDao.getBookmarkedMovie()

    suspend fun insertMovie(movie: List<MovieEntity>) = cinemaDao.insertMovies(movie)

    fun setBookmarkedMovie(movie: MovieEntity, newState: Boolean) {
        movie.isBookmarked = newState
        cinemaDao.updateBookmarkMovie(movie)
    }

}