package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val results: List<com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MovieResponse>,

    @field:SerializedName("total_results")
	val totalResults: Int

)

