package com.fadhlansulistiyo.cinemadatabase.core.utils

import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.TvEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv

object DataMapper {

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
                isBookmarked = false
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
                isBookmarked = it.isBookmarked
            )
        }

    // Map Movie to MovieEntity
    fun mapMovieDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isBookmarked = input.isBookmarked
    )

    // Map TvResponse to TvEntity
    fun mapTvResponsesToEntities(input: List<TvResponse>): List<TvEntity> {
        val tvList = ArrayList<TvEntity>()
        input.map {
            val tv = TvEntity(
                id = it.id,
                name = it.name,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
                isBookmarked = false
            )
            tvList.add(tv)
        }
        return tvList
    }

    // Map TvEntity to Tv
    fun mapTvEntitiesToDomain(input: List<TvEntity>): List<Tv> =
        input.map {
            Tv(
                id = it.id,
                name = it.name,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
                isBookmarked = it.isBookmarked
            )
        }

    // Map Tv to TvEntity
    fun mapTvDomainToEntity(input: Tv) = TvEntity(
        id = input.id,
        name = input.name,
        posterPath = input.posterPath,
        firstAirDate = input.firstAirDate,
        voteAverage = input.voteAverage,
        isBookmarked = input.isBookmarked
    )

    // Map PeopleResponse to PeopleEntity
    fun mapPeopleResponsesToEntities(input: List<PeopleResponse>): List<PeopleEntity> {
        val peopleList = ArrayList<PeopleEntity>()
        input.map {
            val people = PeopleEntity(
                id = it.id,
                name = it.name,
                profilePath = it.profilePath

            )
            peopleList.add(people)
        }
        return peopleList
    }

    // Map PeopleEntity to People
    fun mapPeopleEntitiesToDomain(input: List<PeopleEntity>): List<People> =
        input.map {
            People(
                id = it.id,
                name = it.name,
                profilePath = it.profilePath
            )
        }

    // Map People to PeopleEntity
    fun mapPeopleDomainToEntity(input: People) = PeopleEntity(
        id = input.id,
        name = input.name,
        profilePath = input.profilePath
    )
}