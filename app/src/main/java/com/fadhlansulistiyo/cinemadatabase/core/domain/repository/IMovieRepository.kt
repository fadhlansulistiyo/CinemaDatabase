package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    fun getBookmarkedMovie(): Flow<List<Movie>>
    fun setBookmarkedMovie(movie: Movie, state: Boolean)
    suspend fun getDetailMovie(movieId: Int): Resource<DetailMovie>
}