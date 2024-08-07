package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.GenresResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ProductionCompaniesResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.SeasonsResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Genres
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.ProductionCompanies
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Seasons
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.NA

object BaseMapper {

    fun mapGenresResponseToDomain(input: GenresResponse?): Genres {
        return Genres(
            id = input?.id ?: 0,
            name = input?.name ?: NA
        )
    }

    fun mapProductionCompaniesResponseToDomain(input: ProductionCompaniesResponse?): ProductionCompanies {
        return ProductionCompanies(
            id = input?.id ?: 0,
            name = input?.name ?: NA
        )
    }

    fun mapSeasonsResponseToDomain(input: SeasonsResponse?): Seasons {
        return Seasons(
            id = input?.id ?: 0,
            airDate = input?.airDate ?: "",
            overview = input?.overview ?: NA,
            episodeCount = input?.episodeCount ?: 0,
            voteAverage = input?.voteAverage ?: 0.0,
            name = input?.name ?: NA,
            seasonNumber = input?.seasonNumber ?: 0,
            posterPath = input?.posterPath ?: ""
        )
    }
}