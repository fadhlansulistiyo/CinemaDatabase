package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailTv(
    val id: Int,
    val numberOfEpisodes: Int,
    val backdropPath: String,
    val genres: List<Genres>,
    val numberOfSeasons: Int,
    val firstAirDate: String,
    val overview: String,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanies>,
    val voteAverage: Double,
    val name: String,
    val seasons: List<Seasons>,
)