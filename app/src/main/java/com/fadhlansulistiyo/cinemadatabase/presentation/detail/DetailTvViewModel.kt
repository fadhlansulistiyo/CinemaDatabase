package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
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

    fun fetchTvDetail(tvId: Int) {
        viewModelScope.launch {
            _tvDetail.value = Resource.Loading()
            _tvDetail.value = tvUseCase.getDetailTv(tvId)
            checkIfFavorite(tvDetail.value?.data?.name.toString())
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
}