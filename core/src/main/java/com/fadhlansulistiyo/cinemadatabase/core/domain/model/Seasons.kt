package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class Seasons(
    val id: Int,
    val airDate: String,
    val overview: String,
    val episodeCount: Int,
    val voteAverage: Double,
    val name: String,
    val seasonNumber: Int,
    val posterPath: String
)
