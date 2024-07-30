package com.fadhlansulistiyo.cinemadatabase.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPeopleViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase
) : ViewModel() {

    private val _peopleDetail = MutableLiveData<Resource<DetailPeople>>()
    val peopleDetail: LiveData<Resource<DetailPeople>> get() = _peopleDetail

    fun fetchPeopleDetail(tvId: Int) {
        viewModelScope.launch {
            _peopleDetail.value = Resource.Loading()
            _peopleDetail.value = peopleUseCase.getDetailPeople(tvId)
        }
    }
}