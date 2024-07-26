package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): ListMovieResponse

    @GET("discover/tv?include_adult=false&include_null_first_air_dates=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getAiringTodayTv(
        @Query("api_key") apiKey: String
    ): ListTvResponse

    @GET("trending/person/day")
    suspend fun getTrendingPeople(
        @Query("api_key") apiKey: String
    ): ListPeopleResponse
}