package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListCreditsResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("cast")
	val cast: List<CastResponse> ,
)

data class CastResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("cast_id")
	val castId: Int,

	@field:SerializedName("character")
	val character: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null,
)