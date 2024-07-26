package com.fadhlansulistiyo.cinemadatabase.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.CinemaUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val getDetailMovieUseCase: CinemaUseCase) : ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<DetailMovie>>()
    val movieDetail: LiveData<Resource<DetailMovie>> get() = _movieDetail

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            _movieDetail.value = getDetailMovieUseCase.getDetailMovie(movieId)
        }
    }
}