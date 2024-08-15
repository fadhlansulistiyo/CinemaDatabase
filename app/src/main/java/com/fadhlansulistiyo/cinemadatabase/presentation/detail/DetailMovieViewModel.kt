package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovieWithCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.DATA_IS_NULL
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val watchlistMovieUseCase: WatchlistMovieUseCase
) : ViewModel() {

    private val _detailMovie = MutableLiveData<Resource<DetailMovieWithCast>>()
    val detailMovie: LiveData<Resource<DetailMovieWithCast>> get() = _detailMovie

    private val _isWatchlist = MutableLiveData<Boolean>()
    val isWatchlist: LiveData<Boolean> get() = _isWatchlist

    fun fetchDetailMovie(movieId: Int) {
        viewModelScope.launch {
            _detailMovie.value = Resource.Loading()
            try {
                val result = movieUseCase.getDetailMovie(movieId)
                if (result is Resource.Success) {
                    result.data?.let { detailMovie ->
                        _detailMovie.value = Resource.Success(detailMovie)
                        checkIfWatchlist(detailMovie.detail.title)
                    } ?: run {
                        _detailMovie.value = Resource.Error(DATA_IS_NULL)
                    }
                } else {
                    _detailMovie.value = Resource.Error(result.message ?: UNKNOWN_ERROR)
                }
            } catch (e: Exception) {
                _detailMovie.value = Resource.Error(e.message ?: UNKNOWN_ERROR)
            }
        }
    }

    fun toggleWatchlistMovie(watchlistMovie: WatchlistMovie) {
        viewModelScope.launch {
            val watchlist = watchlistMovieUseCase.getWatchlistByTitle(watchlistMovie.title)
            if (watchlist == null) {
                watchlistMovieUseCase.addWatchlist(watchlistMovie)
                _isWatchlist.postValue(true)
            } else {
                watchlistMovieUseCase.removeWatchlist(watchlistMovie)
                _isWatchlist.postValue(false)
            }
        }
    }

    private fun checkIfWatchlist(title: String) {
        viewModelScope.launch {
            val watchlist = watchlistMovieUseCase.getWatchlistByTitle(title)
            _isWatchlist.postValue(watchlist != null)
        }
    }
}
