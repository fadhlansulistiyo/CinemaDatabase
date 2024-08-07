package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.PeopleMapper

class PopularPeoplePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, PopularPeople>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularPeople> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getPopularPeople(page = page)
            val results = response.results.map {
                PeopleMapper.mapPeopleResponseToDomain(it)
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

    override fun getRefreshKey(state: PagingState<Int, PopularPeople>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}