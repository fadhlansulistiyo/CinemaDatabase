package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiSearchResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {
    fun getMultiSearch(query: String): Flow<PagingData<MultiSearch>>
}