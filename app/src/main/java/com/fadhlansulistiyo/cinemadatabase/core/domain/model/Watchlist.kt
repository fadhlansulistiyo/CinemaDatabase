package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class Watchlist(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
)