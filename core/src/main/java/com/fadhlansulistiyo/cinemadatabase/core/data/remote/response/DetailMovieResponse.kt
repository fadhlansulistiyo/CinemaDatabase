package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("runtime")
    val runtime: Int? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("genres")
    val genres: List<com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.GenresResponse>? = emptyList(),

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("production_companies")
    val productionCompanies: List<com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.ProductionCompaniesResponse>? = emptyList(),

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null

)