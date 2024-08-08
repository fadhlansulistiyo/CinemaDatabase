package com.fadhlansulistiyo.cinemadatabase.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase

class WatchlistMovieTvViewModel(
    watchlistMovieUseCase: WatchlistMovieUseCase,
    watchlistTvUseCase: WatchlistTvUseCase
) : ViewModel() {

    val getWatchlistMovies = watchlistMovieUseCase.getAllWatchlist().asLiveData()
    val getWatchlistTv = watchlistTvUseCase.getAllWatchlist().asLiveData()
}