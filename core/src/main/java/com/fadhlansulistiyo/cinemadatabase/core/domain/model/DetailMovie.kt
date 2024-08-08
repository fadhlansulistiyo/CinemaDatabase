package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val runtime: Int,
    val backdropPath: String,
    val releaseDate: String,
    val genres: List<Genres>,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanies>,
    val voteAverage: Double,
)