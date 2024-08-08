package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun getDetailMovie(movieId: Int): Resource<DetailMovie>
    fun getCast(movieId: Int): Flow<Resource<List<MovieCast>>>
}