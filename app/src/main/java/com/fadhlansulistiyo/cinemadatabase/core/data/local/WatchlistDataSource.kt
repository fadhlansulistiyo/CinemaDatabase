package com.fadhlansulistiyo.cinemadatabase.core.data.local

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db.WatchlistDao
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistDataSource @Inject constructor(private val watchlistDao: WatchlistDao) {
    suspend fun addWatchlist(favorite: WatchlistEntity) = watchlistDao.addWatchlist(favorite)
    suspend fun removeWatchlist(favorite: WatchlistEntity) = watchlistDao.removeWatchlist(favorite)
    suspend fun getWatchlistByTitle(title: String): WatchlistEntity? = watchlistDao.getWatchlistByTitle(title)
    fun getAllWatchlist(): Flow<List<WatchlistEntity>> = watchlistDao.getAllWatchlist()
}