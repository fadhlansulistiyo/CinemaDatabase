package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getNowPlaying(): Flow<Resource<List<Movie>>> {
        return movieRepository.getNowPlaying()
    }

    override suspend fun getDetailMovie(movieId: Int): Resource<DetailMovie> {
        return movieRepository.getDetailMovie(movieId)
    }

    override fun getBookmarkedMovie(): Flow<List<Movie>> {
        return movieRepository.getBookmarkedMovie()
    }

    override fun setBookmarkedMovie(movie: Movie, state: Boolean) {
        return movieRepository.setBookmarkedMovie(movie, state)
    }
}