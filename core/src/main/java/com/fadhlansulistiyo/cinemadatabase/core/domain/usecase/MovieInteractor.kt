package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieDetailWithCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {

    override fun getNowPlaying(): Flow<Resource<List<Movie>>> {
        return movieRepository.getNowPlaying()
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetailWithCast> {
        return movieRepository.getMovieDetail(movieId)
    }
}