package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_NOT_YET_AVAILABLE
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapGenreItemToDomain
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapProductionCompanyResponseToDomain

object MovieMapper {

    // Map MovieResponse to MovieEntity
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
            )
            movieList.add(movie)
        }
        return movieList
    }

    // Map MovieEntity to Movie
    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
            )
        }

    // Map Movie to MovieEntity
    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
    )

    // Map DetailMovieResponse to DetailMovie
    fun mapDetailMovieResponseToDomain(input: DetailMovieResponse): DetailMovie {
        return DetailMovie(
            id = input.id,
            title = input.title ?: DATA_NOT_YET_AVAILABLE,
            originalTitle = input.originalTitle ?: DATA_NOT_YET_AVAILABLE,
            overview = input.overview ?: DATA_NOT_YET_AVAILABLE,
            runtime = input.runtime ?: 0,
            backdropPath = input.backdropPath ?: "",
            revenue = input.revenue ?: 0,
            releaseDate = input.releaseDate ?: DATA_NOT_YET_AVAILABLE,
            genres = input.genres?.map { mapGenreItemToDomain(it) } ?: emptyList(),
            popularity = input.popularity ?: 0.0,
            voteCount = input.voteCount ?: 0,
            budget = input.budget ?: 0,
            posterPath = input.posterPath ?: "",
            productionCompanies = input.productionCompanies?.map { mapProductionCompanyResponseToDomain(it) } ?: emptyList(),
            voteAverage = input.voteAverage ?: 0.0,
            status = input.status ?: DATA_NOT_YET_AVAILABLE,
            isWatchlist = false
        )
    }
}