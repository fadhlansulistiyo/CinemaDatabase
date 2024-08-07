package com.fadhlansulistiyo.cinemadatabase.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")

    val searchResults: LiveData<PagingData<MultiSearch>> = _query
        .debounce(300)
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            searchUseCase.getMultiSearch(query).map { result ->
                result.filter {
                    (it.mediaType == "tv" || it.mediaType == "movie")
                }
            }
        }
        .asLiveData()
        .cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        _query.value = query
    }
}