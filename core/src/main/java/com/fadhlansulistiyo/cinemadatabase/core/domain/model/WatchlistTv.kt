package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class WatchlistTv(
    val id: Int,
    val name: String,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double
)