package com.fadhlansulistiyo.cinemadatabase.core.utils

import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.Movie

object DataMapper {

    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isBookmarked = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isBookmarked = it.isBookmarked
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isBookmarked = input.isBookmarked
    )
}