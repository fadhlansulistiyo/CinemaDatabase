package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import kotlinx.coroutines.flow.Flow

interface WatchlistTvUseCase {
    suspend fun addWatchlist(watchlistTv: WatchlistTv)
    suspend fun removeWatchlist(watchlistTv: WatchlistTv)
    suspend fun getWatchlistByTitle(title: String): WatchlistTv?
    fun getAllWatchlist(): Flow<List<WatchlistTv>>
}