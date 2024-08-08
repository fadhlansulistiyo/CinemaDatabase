package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {

    override fun getNowPlaying(): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<Movie>>> {
        return movieRepository.getNowPlaying()
    }

    override suspend fun getDetailMovie(movieId: Int): com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailMovie> {
        return movieRepository.getDetailMovie(movieId)
    }

    override fun getCast(movieId: Int): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<MovieCast>>> {
        return movieRepository.getCast(movieId)
    }
}