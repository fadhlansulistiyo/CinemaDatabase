package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Watchlist
import kotlinx.coroutines.flow.Flow

interface IWatchlistRepository {
    suspend fun addWatchlist(watchlist: Watchlist)
    suspend fun removeWatchlist(watchlist: Watchlist)
    suspend fun getWatchlistByTitle(title: String): Watchlist?
    fun getAllWatchlist(): Flow<List<Watchlist>>
}