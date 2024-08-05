package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.CastResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_NOT_YET_AVAILABLE
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapGenresResponseToDomain
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapProductionCompaniesResponseToDomain

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
                backdropPath = it.backdropPath
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
                backdropPath = it.backdropPath,
            )
        }

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
            genres = input.genres?.map { mapGenresResponseToDomain(it) } ?: emptyList(),
            popularity = input.popularity ?: 0.0,
            voteCount = input.voteCount ?: 0,
            budget = input.budget ?: 0,
            posterPath = input.posterPath ?: "",
            productionCompanies = input.productionCompanies?.map { mapProductionCompaniesResponseToDomain(it) } ?: emptyList(),
            voteAverage = input.voteAverage ?: 0.0,
            status = input.status ?: DATA_NOT_YET_AVAILABLE,
            isWatchlist = false
        )
    }

    fun mapCastResponseToDomain(input: CastResponse): MovieCast {
        return MovieCast(
            id = input.id,
            castId = input.castId ?: 0,
            name = input.name ?: DATA_NOT_YET_AVAILABLE,
            character = input.character ?: DATA_NOT_YET_AVAILABLE,
            profilePath = input.profilePath ?: "",
        )
    }
}