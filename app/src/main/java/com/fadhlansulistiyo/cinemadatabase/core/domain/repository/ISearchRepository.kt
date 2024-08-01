package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiSearchResponse
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getMultiSearch(query: String): Flow<PagingData<MultiSearchResponse>>
}