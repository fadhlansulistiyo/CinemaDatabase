package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.local.WatchlistTvDataSource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IWatchlistTvRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.WatchlistTvMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistTvRepository @Inject constructor(
    private val watchlistTvDataSource: WatchlistTvDataSource
) : IWatchlistTvRepository {

    override suspend fun addWatchlist(watchlistTv: WatchlistTv) {
        return watchlistTvDataSource.addWatchlist(WatchlistTvMapper.toEntity(watchlistTv))
    }

    override suspend fun removeWatchlist(watchlistTv: WatchlistTv) {
        return watchlistTvDataSource.removeWatchlist(WatchlistTvMapper.toEntity(watchlistTv))
    }

    override suspend fun getWatchlistByTitle(title: String): WatchlistTv? {
        return watchlistTvDataSource.getWatchlistByTitle(title)?.let {
            WatchlistTvMapper.toDomain(it)
        }
    }

    override fun getAllWatchlist(): Flow<List<WatchlistTv>> {
        return watchlistTvDataSource.getAllWatchlist().map { entities ->
            entities.map { WatchlistTvMapper.toDomain(it) }
        }
    }
}