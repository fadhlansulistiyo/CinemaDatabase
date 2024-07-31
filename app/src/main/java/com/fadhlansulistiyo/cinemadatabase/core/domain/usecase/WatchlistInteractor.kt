package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Watchlist
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IWatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistInteractor @Inject constructor(
    private val watchlistRepository: IWatchlistRepository
) : WatchlistUseCase {

    override suspend fun addWatchlist(watchlist: Watchlist) {
        return watchlistRepository.addWatchlist(watchlist)
    }

    override suspend fun removeWatchlist(watchlist: Watchlist) {
        return watchlistRepository.removeWatchlist(watchlist)
    }

    override suspend fun getWatchlistByTitle(title: String): Watchlist? {
        return watchlistRepository.getWatchlistByTitle(title)
    }

    override fun getAllWatchlist(): Flow<List<Watchlist>> {
        return watchlistRepository.getAllWatchlist()
    }
}