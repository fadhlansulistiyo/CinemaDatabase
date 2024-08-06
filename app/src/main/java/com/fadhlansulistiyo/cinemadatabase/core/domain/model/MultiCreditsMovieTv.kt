package com.fadhlansulistiyo.cinemadatabase.core.domain.model

import com.google.gson.annotations.SerializedName

data class MultiCreditsMovieTv(
    val id: Int,
    val posterPath: String? = null,
    val mediaType: String? = null,
    val voteAverage: Double? = null,
    val title: String? = null,
    val releaseDate: String? = null,
)