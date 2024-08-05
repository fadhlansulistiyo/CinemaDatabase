package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(
    private val tvUseCase: TvUseCase,
    private val watchlistTvUSeCase: WatchlistTvUseCase
) : ViewModel() {

    private val _tvDetail = MutableLiveData<Resource<DetailTv>>()
    val tvDetail: LiveData<Resource<DetailTv>> get() = _tvDetail

    private val _isWatchlist = MutableLiveData<Boolean>()
    val isWatchlist: LiveData<Boolean> get() = _isWatchlist

    private val _tvCast = MutableLiveData<Resource<List<TvCast>>>()
    val tvCast: LiveData<Resource<List<TvCast>>> = _tvCast

    fun fetchTvDetail(tvId: Int) {
        viewModelScope.launch {
            try {
                _tvDetail.value = Resource.Loading()
                val detailResult = tvUseCase.getDetailTv(tvId)
                _tvDetail.value = detailResult
                detailResult.data?.name?.let { checkIfFavorite(it) }
                fetchCast(tvId)
            } catch (e: Exception) {
                _tvDetail.value = Resource.Error(e.message ?: UNKNOWN_ERROR)
            }
        }
    }

    fun setUserFavorite(watchlistTv: WatchlistTv) {
        viewModelScope.launch {
            val watchlist = watchlistTvUSeCase.getWatchlistByTitle(watchlistTv.name)
            if (watchlist == null) {
                watchlistTvUSeCase.addWatchlist(watchlistTv)
                _isWatchlist.postValue(true)
            } else {
                watchlistTvUSeCase.removeWatchlist(watchlistTv)
                _isWatchlist.postValue(false)
            }
        }
    }

    private fun checkIfFavorite(title: String) {
        viewModelScope.launch {
            val watchlist = watchlistTvUSeCase.getWatchlistByTitle(title)
            _isWatchlist.postValue(watchlist != null)
        }
    }

    private fun fetchCast(seriesId: Int) {
        viewModelScope.launch {
            try {
                tvUseCase.getCast(seriesId).collect {
                    _tvCast.postValue(it)
                }
            } catch (e: Exception) {
                _tvCast.postValue(Resource.Error(e.message ?: UNKNOWN_ERROR))
            }
        }
    }

}