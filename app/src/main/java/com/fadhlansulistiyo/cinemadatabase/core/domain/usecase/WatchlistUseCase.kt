package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Watchlist
import kotlinx.coroutines.flow.Flow

interface WatchlistUseCase {
    suspend fun addWatchlist(watchlist: Watchlist)
    suspend fun removeWatchlist(watchlist: Watchlist)
    suspend fun getWatchlistByTitle(title: String): Watchlist?
    fun getAllWatchlist(): Flow<List<Watchlist>>
}