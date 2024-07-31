package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistUseCase
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.WatchlistMapper
import com.fadhlansulistiyo.cinemadatabase.presentation.model.WatchlistUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val watchlistUseCase: WatchlistUseCase
) : ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<DetailMovie>>()
    val movieDetail: LiveData<Resource<DetailMovie>> get() = _movieDetail

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            _movieDetail.value = movieUseCase.getDetailMovie(movieId)
            checkIfFavorite(movieId.toString())
        }
    }

    fun setUserFavorite(watchlistUI: WatchlistUI) {
        viewModelScope.launch {
            val watchlist = watchlistUseCase.getWatchlistByTitle(watchlistUI.title)
            if (watchlist == null) {
                watchlistUseCase.addWatchlist(WatchlistMapper.fromUI(watchlistUI))
                _isFavorite.postValue(true)
            } else {
                watchlistUseCase.removeWatchlist(WatchlistMapper.fromUI(watchlistUI))
                _isFavorite.postValue(false)
            }
        }
    }

    private fun checkIfFavorite(title: String) {
        viewModelScope.launch {
            val watchlist = watchlistUseCase.getWatchlistByTitle(title)
            _isFavorite.postValue(watchlist != null)
        }
    }
}