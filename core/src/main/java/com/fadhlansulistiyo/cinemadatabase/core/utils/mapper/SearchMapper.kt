package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiSearchResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.NA

object SearchMapper {
    fun mapMultiSearchResponseToDomain(input: MultiSearchResponse): MultiSearch {
        return MultiSearch(
            id = input.id,
            title = input.title ?: NA,
            posterPath = input.posterPath ?: "",
            releaseDate = input.releaseDate ?: "",
            voteAverage = input.voteAverage ?: 0.0,
            mediaType = input.mediaType ?: NA,
            overview = input.overview ?: NA
        )
    }
}