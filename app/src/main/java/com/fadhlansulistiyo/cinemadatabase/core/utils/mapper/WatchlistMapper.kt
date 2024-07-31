package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.WatchlistEntity
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Watchlist
import com.fadhlansulistiyo.cinemadatabase.presentation.model.WatchlistUI

object WatchlistMapper {
    fun toEntity(domain: Watchlist): WatchlistEntity {
        return WatchlistEntity(
            id = domain.id,
            title = domain.title,
            posterPath = domain.posterPath,
            releaseDate = domain.releaseDate,
            voteAverage = domain.voteAverage
        )
    }

    fun toDomain(entity: WatchlistEntity): Watchlist {
        return Watchlist(
            id = entity.id,
            title = entity.title,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            voteAverage = entity.voteAverage
        )
    }

    fun toUI(domain: Watchlist): WatchlistUI {
        return WatchlistUI(
            id = domain.id,
            title = domain.title,
            posterPath = domain.posterPath,
            releaseDate = domain.releaseDate,
            voteAverage = domain.voteAverage
        )
    }

    fun fromUI(ui: WatchlistUI): Watchlist {
        return Watchlist(
            id = ui.id,
            title = ui.title,
            posterPath = ui.posterPath,
            releaseDate = ui.releaseDate,
            voteAverage = ui.voteAverage
        )
    }
}