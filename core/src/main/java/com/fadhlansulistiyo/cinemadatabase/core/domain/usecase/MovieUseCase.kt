package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieDetailWithCast
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetailWithCast>
}