package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IWatchlistMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistMovieInteractor @Inject constructor(
    private val watchlistRepository: IWatchlistMovieRepository
) : WatchlistMovieUseCase {

    override suspend fun addWatchlist(watchlistMovie: WatchlistMovie) {
        return watchlistRepository.addWatchlist(watchlistMovie)
    }

    override suspend fun removeWatchlist(watchlistMovie: WatchlistMovie) {
        return watchlistRepository.removeWatchlist(watchlistMovie)
    }

    override suspend fun getWatchlistByTitle(title: String): WatchlistMovie? {
        return watchlistRepository.getWatchlistByTitle(title)
    }

    override fun getAllWatchlist(): Flow<List<WatchlistMovie>> {
        return watchlistRepository.getAllWatchlist()
    }
}