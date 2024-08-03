package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiSearch

class SearchPagingSource(
    private val apiService: ApiService,
    private var searchParam: String
) : PagingSource<Int, MultiSearch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MultiSearch> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getMultiSearch(query = searchParam, page = page)
            val results = response.results.map {
                MultiSearch(
                    id = it.id,
                    title = it.title,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    voteAverage = it.voteAverage,
                    mediaType = it.mediaType
                )
            }
            LoadResult.Page(
                data = results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.totalPages > page) page + 1 else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MultiSearch>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}