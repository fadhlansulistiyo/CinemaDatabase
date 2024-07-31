package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.WatchlistTvMapper
import com.fadhlansulistiyo.cinemadatabase.presentation.model.WatchlistTvUI
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
        }
    }

    fun setUserFavorite(watchlistTvUI: WatchlistTvUI) {
        viewModelScope.launch {
            val watchlist = watchlistTvUSeCase.getWatchlistByTitle(watchlistTvUI.name)
            if (watchlist == null) {
                watchlistTvUSeCase.addWatchlist(WatchlistTvMapper.fromUI(watchlistTvUI))
                _isWatchlist.postValue(true)
            } else {
                watchlistTvUSeCase.removeWatchlist(WatchlistTvMapper.fromUI(watchlistTvUI))
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