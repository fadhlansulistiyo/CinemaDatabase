package com.fadhlansulistiyo.cinemadatabase.core.data.remote.source

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiService
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.CastResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseRemoteDataSource() {

    fun getNowPlaying(): Flow<ApiResponseResult<List<MovieResponse>>> {
        return flowApiCall {
            val response = apiService.getNowPlaying()
            response.results.ifEmpty { throw EmptyDataException() }
        }
    }

    private suspend fun getDetailMovie(movieId: Int): ApiResponseResult<DetailMovieResponse> {
        return handleApiCall {
            apiService.getDetailMovie(movieId)
        }
    }

    private suspend fun getCast(movieId: Int): ApiResponseResult<List<CastResponse>> {
        return handleApiCall {
            apiService.getMovieCredits(movieId).cast
        }
    }

    suspend fun getDetailMovieWithCast(
        movieId: Int
    ): Pair<ApiResponseResult<DetailMovieResponse>, ApiResponseResult<List<CastResponse>>> {
        val detail = getDetailMovie(movieId)
        val cast = getCast(movieId)
        return Pair(detail, cast)
    }
}
