package com.fadhlansulistiyo.cinemadatabase.core.domain

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CinemaInteractor @Inject constructor(private val cinemaRepository: ICinemaRepository) :
    CinemaUseCase {
    override fun getNowPlaying(): Flow<Resource<List<Movie>>> {
        return cinemaRepository.getNowPlaying()
    }

    override fun getAiringTodayTv(): Flow<Resource<List<Tv>>> {
        return cinemaRepository.getAiringTodayTv()
    }

    override fun getBookmarkedMovie(): Flow<List<Movie>> {
        return cinemaRepository.getBookmarkedMovie()
    }

    override fun setBookmarkedMovie(movie: Movie, state: Boolean) {
        return cinemaRepository.setBookmarkedMovie(movie, state)
    }
}