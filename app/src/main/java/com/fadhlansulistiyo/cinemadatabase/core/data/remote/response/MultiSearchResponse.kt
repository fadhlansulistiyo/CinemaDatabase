package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class MultiSearchResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title", alternate = ["name"])
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("release_date", alternate = ["first_air_date"])
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null

)