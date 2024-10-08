package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistTvEntity
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv

object WatchlistTvMapper {
    fun toEntity(domain: WatchlistTv): WatchlistTvEntity {
        return WatchlistTvEntity(
            id = domain.id,
            name = domain.name,
            posterPath = domain.posterPath,
            firstAirDate = domain.firstAirDate,
            voteAverage = domain.voteAverage
        )
    }

    fun toDomain(entity: WatchlistTvEntity): WatchlistTv {
        return WatchlistTv(
            id = entity.id,
            name = entity.name,
            posterPath = entity.posterPath,
            firstAirDate = entity.firstAirDate,
            voteAverage = entity.voteAverage
        )
    }
}