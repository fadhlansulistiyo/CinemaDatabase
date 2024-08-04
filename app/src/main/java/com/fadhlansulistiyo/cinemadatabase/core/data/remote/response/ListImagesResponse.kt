package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListImagesResponse(

	@field:SerializedName("backdrops")
	val backdrops: List<BackdropsItem?>? = null,

	@field:SerializedName("posters")
	val posters: List<PosterResponse?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("logos")
	val logos: List<LogosItem?>? = null
)

data class PosterResponse(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Double? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class LogosItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Double? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class BackdropsItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: Double? = null,

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: Any? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)