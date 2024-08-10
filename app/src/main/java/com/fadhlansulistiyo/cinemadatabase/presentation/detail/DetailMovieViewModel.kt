package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieDetailWithCast
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

    private val _movieDetailWithCast = MutableLiveData<Resource<MovieDetailWithCast>>()
    val movieDetailWithCast: LiveData<Resource<MovieDetailWithCast>> get() = _movieDetailWithCast

    private val _isWatchlist = MutableLiveData<Boolean>()
    val isWatchlist: LiveData<Boolean> get() = _isWatchlist

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetailWithCast.value = Resource.Loading()
            try {
                val result = movieUseCase.getMovieDetail(movieId)
                if (result is Resource.Success) {
                    result.data?.let { movieDetailWithCast ->
                        _movieDetailWithCast.value = Resource.Success(movieDetailWithCast)
                        checkIfWatchlist(movieDetailWithCast.detail.title)
                    } ?: run {
                        _movieDetailWithCast.value = Resource.Error(DATA_IS_NULL)
                    }
                } else {
                    _movieDetailWithCast.value = Resource.Error(result.message ?: UNKNOWN_ERROR)
                }
            } catch (e: Exception) {
                _movieDetailWithCast.value = Resource.Error(e.message ?: UNKNOWN_ERROR)
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
