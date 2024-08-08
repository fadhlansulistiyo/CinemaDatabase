package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailPeopleResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("known_for_department")
	val knownForDepartment: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null,

	@field:SerializedName("biography")
	val biography: String? = null,

	@field:SerializedName("place_of_birth")
	val placeOfBirth: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

)
