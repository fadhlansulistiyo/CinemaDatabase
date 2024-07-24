package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): ListMovieResponse
}