package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.WatchlistMovieMapper
import com.fadhlansulistiyo.cinemadatabase.presentation.model.WatchlistMovieUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val watchlistMovieUseCase: WatchlistMovieUseCase
) : ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<DetailMovie>>()
    val movieDetail: LiveData<Resource<DetailMovie>> get() = _movieDetail

    private val _isWatchlist = MutableLiveData<Boolean>()
    val isWatchlist: LiveData<Boolean> get() = _isWatchlist

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            _movieDetail.value = movieUseCase.getDetailMovie(movieId)
            checkIfFavorite(movieId.toString())
        }
    }

    fun setUserFavorite(watchlistMovieUI: WatchlistMovieUI) {
        viewModelScope.launch {
            val watchlist = watchlistMovieUseCase.getWatchlistByTitle(watchlistMovieUI.title)
            if (watchlist == null) {
                watchlistMovieUseCase.addWatchlist(WatchlistMovieMapper.fromUI(watchlistMovieUI))
                _isWatchlist.postValue(true)
            } else {
                watchlistMovieUseCase.removeWatchlist(WatchlistMovieMapper.fromUI(watchlistMovieUI))
                _isWatchlist.postValue(false)
            }
        }
    }

    private fun checkIfFavorite(title: String) {
        viewModelScope.launch {
            val watchlist = watchlistMovieUseCase.getWatchlistByTitle(title)
            _isWatchlist.postValue(watchlist != null)
        }
    }
}