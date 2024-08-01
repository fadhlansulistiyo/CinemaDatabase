package com.fadhlansulistiyo.cinemadatabase.presentation.watchlist.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistMovieViewModel @Inject constructor(
    watchlistMovieUseCase: WatchlistMovieUseCase,
    watchlistTvUseCase: WatchlistTvUseCase
) : ViewModel() {

    val getWatchlistMovies = watchlistMovieUseCase.getAllWatchlist().asLiveData()
    val getWatchlistTv = watchlistTvUseCase.getAllWatchlist().asLiveData()
}