package com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response

import com.google.gson.annotations.SerializedName

data class GenresItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String? = null,
)
