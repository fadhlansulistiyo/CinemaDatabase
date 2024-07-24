package com.fadhlansulistiyo.cinemadatabase.core.domain

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface CinemaUseCase {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    fun getBookmarkedMovie(): Flow<List<Movie>>
    fun setBookmarkedMovie(movie: Movie, state: Boolean)
}