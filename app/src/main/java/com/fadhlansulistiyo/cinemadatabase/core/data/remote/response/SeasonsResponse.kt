package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SeasonsResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("air_date")
    val airDate: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("episode_count")
    val episodeCount: Int? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("season_number")
    val seasonNumber: Int? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null

)
