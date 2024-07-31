package com.fadhlansulistiyo.cinemadatabase.presentation.model

data class WatchlistUI(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
)