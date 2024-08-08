package com.fadhlansulistiyo.cinemadatabase.core.data.local

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db.WatchlistTvDao
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistTvEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistTvDataSource @Inject constructor(private val watchlistDao: WatchlistTvDao) {
    suspend fun addWatchlist(watchlist: WatchlistTvEntity) = watchlistDao.addWatchlist(watchlist)
    suspend fun removeWatchlist(watchlist: WatchlistTvEntity) = watchlistDao.removeWatchlist(watchlist)
    suspend fun getWatchlistByTitle(title: String): WatchlistTvEntity? = watchlistDao.getWatchlistByTitle(title)
    fun getAllWatchlist(): Flow<List<WatchlistTvEntity>> = watchlistDao.getAllWatchlist()
}