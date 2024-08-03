package com.fadhlansulistiyo.cinemadatabase.presentation.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    peopleUseCase: PeopleUseCase
) : ViewModel() {

    val popularPeople: LiveData<PagingData<PopularPeople>> = peopleUseCase.getPopularPeople()
        .cachedIn(viewModelScope)
        .asLiveData()
}