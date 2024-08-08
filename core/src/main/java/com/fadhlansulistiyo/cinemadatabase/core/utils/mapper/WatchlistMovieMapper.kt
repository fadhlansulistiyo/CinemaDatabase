package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistMovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie

object WatchlistMovieMapper {
    fun toEntity(domain: WatchlistMovie): WatchlistMovieEntity {
        return WatchlistMovieEntity(
            id = domain.id,
            title = domain.title,
            posterPath = domain.posterPath,
            releaseDate = domain.releaseDate,
            voteAverage = domain.voteAverage
        )
    }

    fun toDomain(entity: WatchlistMovieEntity): WatchlistMovie {
        return WatchlistMovie(
            id = entity.id,
            title = entity.title,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            voteAverage = entity.voteAverage
        )
    }
}