package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleUseCase
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPeopleViewModel @Inject constructor(
    private val peopleUseCase: PeopleUseCase
) : ViewModel() {

    private val _peopleDetail = MutableLiveData<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailPeople>>()
    val peopleDetail: LiveData<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailPeople>> get() = _peopleDetail

    private val _credits = MutableLiveData<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<MultiCreditsMovieTv>>>()
    val credits: LiveData<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<MultiCreditsMovieTv>>> = _credits

    fun fetchPeopleDetail(tvId: Int) {
        viewModelScope.launch {
            try {
                _peopleDetail.value = com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Loading()
                val detailResult = peopleUseCase.getDetailPeople(tvId)
                _peopleDetail.value = detailResult
                fetchCredits(tvId)
            } catch (e: Exception) {
                _peopleDetail.value = com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(e.message ?: UNKNOWN_ERROR)
            }
        }
    }

    private fun fetchCredits(id: Int) {
        viewModelScope.launch {
            try {
                peopleUseCase.getCredits(id).collect {
                    _credits.postValue(it)
                }
            } catch (e: Exception) {
                _credits.postValue(com.fadhlansulistiyo.cinemadatabase.core.data.Resource.Error(e.message ?: UNKNOWN_ERROR))
            }
        }
    }
}