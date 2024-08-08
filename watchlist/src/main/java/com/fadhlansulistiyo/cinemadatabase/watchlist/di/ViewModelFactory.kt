package com.fadhlansulistiyo.cinemadatabase.watchlist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
import com.fadhlansulistiyo.cinemadatabase.watchlist.viewmodel.WatchlistMovieTvViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val watchlistMovieUseCase: WatchlistMovieUseCase,
    private val watchlistTvUseCase: WatchlistTvUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(WatchlistMovieTvViewModel::class.java) -> {
                WatchlistMovieTvViewModel(watchlistMovieUseCase, watchlistTvUseCase) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}