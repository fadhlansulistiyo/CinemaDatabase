package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import kotlinx.coroutines.flow.Flow

interface IWatchlistTvRepository {
    suspend fun addWatchlist(watchlistTv: WatchlistTv)
    suspend fun removeWatchlist(watchlistTv: WatchlistTv)
    suspend fun getWatchlistByTitle(title: String): WatchlistTv?
    fun getAllWatchlist(): Flow<List<WatchlistTv>>
}