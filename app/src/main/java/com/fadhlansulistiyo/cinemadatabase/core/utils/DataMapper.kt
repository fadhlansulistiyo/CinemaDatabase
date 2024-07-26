package com.fadhlansulistiyo.cinemadatabase.core.utils

import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.TvEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.GenresItem
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.MovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ProductionCompaniesResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Genres
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.ProductionCompanies
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_NOT_YET_AVAILABLE

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
            status = input.status ?: DATA_NOT_YET_AVAILABLE
        )
    }

    private fun mapGenreItemToDomain(input: GenresItem?): Genres {
        return Genres(
            id = input?.id ?: 0,
            name = input?.name ?: ""
        )
    }

    private fun mapProductionCompanyResponseToDomain(input: ProductionCompaniesResponse?): ProductionCompanies {
        return ProductionCompanies(
            id = input?.id ?: 0,
            logoPath = input?.logoPath,
            name = input?.name ?: ""
        )
    }
}