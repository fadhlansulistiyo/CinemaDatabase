package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.CastResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.NA
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

    // Map MovieEntity to Domain
    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title ?: NA,
                posterPath = it.posterPath ?: "",
                releaseDate = it.releaseDate ?: "",
                voteAverage = it.voteAverage ?: 0.0,
                backdropPath = it.backdropPath ?: "",
            )
        }

    // Map DetailMovieResponse to DetailMovie
    fun mapDetailMovieResponseToDomain(input: DetailMovieResponse): DetailMovie {
        return DetailMovie(
            id = input.id,
            title = input.title ?: NA,
            overview = input.overview ?: NA,
            runtime = input.runtime ?: 0,
            backdropPath = input.backdropPath ?: "",
            releaseDate = input.releaseDate ?: "",
            genres = input.genres?.map { mapGenresResponseToDomain(it) } ?: emptyList(),
            posterPath = input.posterPath ?: "",
            productionCompanies = input.productionCompanies?.map { mapProductionCompaniesResponseToDomain(it) } ?: emptyList(),
            voteAverage = input.voteAverage ?: 0.0,
        )
    }

    fun mapCastResponseToDomain(input: CastResponse): MovieCast {
        return MovieCast(
            id = input.id,
            castId = input.castId ?: 0,
            name = input.name ?: NA,
            character = input.character ?: NA,
            profilePath = input.profilePath ?: "",
        )
    }
}