package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class MultiCreditsMovieTv(
    val id: Int,
    val posterPath: String,
    val mediaType: String,
    val voteAverage: Double,
    val title: String,
    val releaseDate: String,
)