package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.GenresResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ProductionCompaniesResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.SeasonsResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Genres
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.ProductionCompanies
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Seasons

object BaseMapper {

    fun mapGenresResponseToDomain(input: GenresResponse?): Genres {
        return Genres(
            id = input?.id ?: 0,
            name = input?.name ?: ""
        )
    }

    fun mapProductionCompaniesResponseToDomain(input: ProductionCompaniesResponse?): ProductionCompanies {
        return ProductionCompanies(
            id = input?.id ?: 0,
            logoPath = input?.logoPath,
            name = input?.name ?: ""
        )
    }

    fun mapSeasonsResponseToDomain(input: SeasonsResponse?): Seasons {
        return Seasons(
            id = input?.id ?: 0,
            airDate = input?.airDate,
            overview = input?.overview,
            episodeCount = input?.episodeCount,
            voteAverage = input?.voteAverage,
            name = input?.name,
            seasonNumber = input?.seasonNumber,
            posterPath = input?.posterPath
        )
    }
}