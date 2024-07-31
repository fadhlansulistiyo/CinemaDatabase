package com.fadhlansulistiyo.cinemadatabase.core.data.local

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db.MovieDao
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()
    suspend fun insertMovie(movie: List<MovieEntity>) = movieDao.insertMovies(movie)
}