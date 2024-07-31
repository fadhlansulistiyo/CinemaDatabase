package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.local.WatchlistDataSource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Watchlist
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IWatchlistRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.WatchlistMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistRepository @Inject constructor(
    private val watchlistDataSource: WatchlistDataSource
) : IWatchlistRepository {

    override suspend fun addWatchlist(watchlist: Watchlist) {
        watchlistDataSource.addWatchlist(WatchlistMapper.toEntity(watchlist))
    }

    override suspend fun removeWatchlist(watchlist: Watchlist) {
        watchlistDataSource.removeWatchlist(WatchlistMapper.toEntity(watchlist))
    }

    override suspend fun getWatchlistByTitle(title: String): Watchlist? {
        val entity = watchlistDataSource.getWatchlistByTitle(title)
        return entity?.let { WatchlistMapper.toDomain(it) }
    }

    override fun getAllWatchlist(): Flow<List<Watchlist>> {
        return watchlistDataSource.getAllWatchlist().map { entities ->
            entities.map { WatchlistMapper.toDomain(it) }
        }
    }
}