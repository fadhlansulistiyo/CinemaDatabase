package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
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

    private val _movieCast = MutableLiveData<Resource<List<MovieCast>>>()
    val movieCast: LiveData<Resource<List<MovieCast>>> = _movieCast

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                _movieDetail.value = Resource.Loading()
                val detailResult = movieUseCase.getDetailMovie(movieId)
                _movieDetail.value = detailResult
                detailResult.data?.title?.let { checkIfWatchlist(it) }
                fetchCast(movieId)
            } catch (e: Exception) {
                _movieDetail.value = Resource.Error(e.message ?: "Unknown error")
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

    private fun fetchCast(movieId: Int) {
        viewModelScope.launch {
            try {
                movieUseCase.getCast(movieId).collect {
                    _movieCast.postValue(it)
                }
            } catch (e: Exception) {
                _movieCast.postValue(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }
}