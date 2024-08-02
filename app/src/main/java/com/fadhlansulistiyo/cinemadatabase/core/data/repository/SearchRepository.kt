package com.fadhlansulistiyo.cinemadatabase.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.SearchPagingSource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val apiService: ApiService
) : ISearchRepository {

    override fun getMultiSearch(query: String): Flow<PagingData<MultiSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(apiService, query) }
        ).flow
    }
}