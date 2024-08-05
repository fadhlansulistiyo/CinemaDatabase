package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailTv(
    val id: Int,
    val numberOfEpisodes: Int? = null,
    val backdropPath: String? = null,
    val genres: List<Genres>,
    val numberOfSeasons: Int? = null,
    val firstAirDate: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompanies>,
    val voteAverage: Double? = null,
    val name: String? = null,
    val seasons: List<Seasons>,
)