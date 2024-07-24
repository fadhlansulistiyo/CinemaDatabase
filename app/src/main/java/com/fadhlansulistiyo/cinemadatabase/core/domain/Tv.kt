package com.fadhlansulistiyo.cinemadatabase.core.domain

data class Tv(
    val id: Int,
    val name: String,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double,
    val isBookmarked: Boolean
)