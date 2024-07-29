package com.fadhlansulistiyo.cinemadatabase.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(
    private val tvUseCase: TvUseCase
) : ViewModel() {

    private val _tvDetail = MutableLiveData<Resource<DetailTv>>()
    val tvDetail: LiveData<Resource<DetailTv>> get() = _tvDetail

    fun fetchMovieDetail(tvId: Int) {
        viewModelScope.launch {
            _tvDetail.value = Resource.Loading()
            _tvDetail.value = tvUseCase.getDetailTv(tvId)
        }
    }
}