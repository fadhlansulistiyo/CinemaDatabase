package com.fadhlansulistiyo.cinemadatabase.core.domain

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface ICinemaRepository {
    fun getNowPlaying(): Flow<Resource<List<Movie>>>
    fun getAiringTodayTv(): Flow<Resource<List<Tv>>>
    fun getTrendingPeople(): Flow<Resource<List<People>>>
    fun getBookmarkedMovie(): Flow<List<Movie>>
    fun setBookmarkedMovie(movie: Movie, state: Boolean)
}