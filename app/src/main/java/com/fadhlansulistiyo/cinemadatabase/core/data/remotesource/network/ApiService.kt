package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.network

import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.DetailTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.ListTvResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getNowPlaying(): ListMovieResponse

    @GET("discover/tv?include_adult=false&include_null_first_air_dates=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getAiringTodayTv(): ListTvResponse

    @GET("trending/person/day")
    suspend fun getTrendingPeople(): ListPeopleResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int
    ): DetailMovieResponse

    @GET("tv/{series_id}")
    suspend fun getDetailTv(
        @Path("series_id") seriesId: Int
    ): DetailTvResponse
}