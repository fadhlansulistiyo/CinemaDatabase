package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiSearchResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val searchParam: String
) : PagingSource<Int, MultiSearchResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MultiSearchResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MultiSearchResponse> {
        return try {
            val nextPage = params.key ?: 1
            val multiSearch = apiService.getMultiSearch(
                page = nextPage,
                query = searchParam
            )
            LoadResult.Page(
                data = multiSearch.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (multiSearch.results.isEmpty()) null else multiSearch.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }
}