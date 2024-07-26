package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    fun getBookmarkedMovie(): Flow<List<Movie>>
    fun setBookmarkedMovie(movie: Movie, state: Boolean)
    suspend fun getDetailMovie(movieId: Int): Resource<DetailMovie>
}