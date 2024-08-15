package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovieWithCast
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun getDetailMovie(movieId: Int): Resource<DetailMovieWithCast>
}