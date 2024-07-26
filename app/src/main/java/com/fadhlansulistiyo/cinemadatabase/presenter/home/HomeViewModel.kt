package com.fadhlansulistiyo.cinemadatabase.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieUseCase: MovieUseCase,
    tvUseCase: TvUseCase,
    peopleUseCase: PeopleUseCase
) : ViewModel() {
    val getNowPlaying = movieUseCase.getNowPlaying().asLiveData()
    val getAiringTodayTv = tvUseCase.getAiringTodayTv().asLiveData()
    val getTrendingPeople = peopleUseCase.getTrendingPeople().asLiveData()
}