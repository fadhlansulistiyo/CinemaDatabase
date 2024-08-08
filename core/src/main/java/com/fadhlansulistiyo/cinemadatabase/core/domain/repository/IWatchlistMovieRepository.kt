package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import kotlinx.coroutines.flow.Flow

interface IWatchlistMovieRepository {
    suspend fun addWatchlist(watchlistMovie: WatchlistMovie)
    suspend fun removeWatchlist(watchlistMovie: WatchlistMovie)
    suspend fun getWatchlistByTitle(title: String): WatchlistMovie?
    fun getAllWatchlist(): Flow<List<WatchlistMovie>>
}