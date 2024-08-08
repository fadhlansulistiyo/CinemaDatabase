package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getNowPlaying(): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<Movie>>>
    suspend fun getDetailMovie(movieId: Int): com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailMovie>
    fun getCast(movieId: Int): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<MovieCast>>>
}