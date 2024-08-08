package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.local.WatchlistMovieDataSource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IWatchlistMovieRepository
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.WatchlistMovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistMovieRepository @Inject constructor(
    private val watchlistMovieDataSource: WatchlistMovieDataSource
) : IWatchlistMovieRepository {

    override suspend fun addWatchlist(watchlistMovie: WatchlistMovie) {
        return watchlistMovieDataSource.addWatchlist(WatchlistMovieMapper.toEntity(watchlistMovie))
    }

    override suspend fun removeWatchlist(watchlistMovie: WatchlistMovie) {
        return watchlistMovieDataSource.removeWatchlist(WatchlistMovieMapper.toEntity(watchlistMovie))
    }

    override suspend fun getWatchlistByTitle(title: String): WatchlistMovie? {
        return watchlistMovieDataSource.getWatchlistByTitle(title)
            ?.let { WatchlistMovieMapper.toDomain(it) }
    }

    override fun getAllWatchlist(): Flow<List<WatchlistMovie>> {
        return watchlistMovieDataSource.getAllWatchlist().map { entities ->
            entities.map { WatchlistMovieMapper.toDomain(it) }
        }
    }
}