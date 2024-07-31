package com.fadhlansulistiyo.cinemadatabase.presentation.model

data class WatchlistTvUI(
    val id: Int,
    val name: String,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double
)