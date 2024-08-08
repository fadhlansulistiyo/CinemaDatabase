package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import kotlinx.coroutines.flow.Flow

interface WatchlistMovieUseCase {
    suspend fun addWatchlist(watchlistMovie: WatchlistMovie)
    suspend fun removeWatchlist(watchlistMovie: WatchlistMovie)
    suspend fun getWatchlistByTitle(title: String): WatchlistMovie?
    fun getAllWatchlist(): Flow<List<WatchlistMovie>>
}