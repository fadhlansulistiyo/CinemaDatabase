package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.GenresItem
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ProductionCompaniesResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Genres
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.ProductionCompanies

object BaseMapper {

    fun mapGenreItemToDomain(input: GenresItem?): Genres {
        return Genres(
            id = input?.id ?: 0,
            name = input?.name ?: ""
        )
    }

    fun mapProductionCompanyResponseToDomain(input: ProductionCompaniesResponse?): ProductionCompanies {
        return ProductionCompanies(
            id = input?.id ?: 0,
            logoPath = input?.logoPath,
            name = input?.name ?: ""
        )
    }
}