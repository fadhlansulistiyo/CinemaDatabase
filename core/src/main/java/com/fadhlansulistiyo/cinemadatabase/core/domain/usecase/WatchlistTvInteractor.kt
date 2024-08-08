package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IWatchlistTvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistTvInteractor @Inject constructor(
    private val watchlistRepository: IWatchlistTvRepository
) : WatchlistTvUseCase {

    override suspend fun addWatchlist(watchlistTv: WatchlistTv) {
        return watchlistRepository.addWatchlist(watchlistTv)
    }

    override suspend fun removeWatchlist(watchlistTv: WatchlistTv) {
        return watchlistRepository.removeWatchlist(watchlistTv)
    }

    override suspend fun getWatchlistByTitle(title: String): WatchlistTv? {
        return watchlistRepository.getWatchlistByTitle(title)
    }

    override fun getAllWatchlist(): Flow<List<WatchlistTv>> {
        return watchlistRepository.getAllWatchlist()
    }

}