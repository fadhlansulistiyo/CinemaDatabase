package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val isBookmarked: Boolean
)