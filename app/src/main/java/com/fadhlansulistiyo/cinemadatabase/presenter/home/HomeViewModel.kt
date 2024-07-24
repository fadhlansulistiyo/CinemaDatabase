package com.fadhlansulistiyo.cinemadatabase.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadhlansulistiyo.cinemadatabase.core.domain.CinemaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(cinemaUseCase: CinemaUseCase) : ViewModel() {
    val getNowPlaying = cinemaUseCase.getNowPlaying().asLiveData()
    val getAiringTodayTv = cinemaUseCase.getAiringTodayTv().asLiveData()
}