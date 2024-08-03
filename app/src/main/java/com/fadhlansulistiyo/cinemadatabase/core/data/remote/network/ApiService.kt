package com.fadhlansulistiyo.cinemadatabase.core.data.remote.network

import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ListMovieResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ListMultiSearchResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ListPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ListTvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("person/{person_id}")
    suspend fun getDetailPeople(
        @Path("person_id") personId: Int
    ): DetailPeopleResponse

    @GET("search/multi")
    suspend fun getMultiSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en"
    ): ListMultiSearchResponse

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int = 0,
        @Query("language") language: String = "en"
    ): ListPeopleResponse
}