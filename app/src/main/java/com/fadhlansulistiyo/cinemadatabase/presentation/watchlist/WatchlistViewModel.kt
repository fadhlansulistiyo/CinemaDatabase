package com.fadhlansulistiyo.cinemadatabase.presentation.watchlist

import androidx.lifecycle.ViewModel
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    watchlistUseCase: WatchlistUseCase
) : ViewModel() {

    // code for watchlist here

}