package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailTv(
    val id: Int,
    val originalLanguage: String? = null,
    val numberOfEpisodes: Int? = null,
    val backdropPath: String? = null,
    val genres: List<Genres?>? = null,
    val popularity: Double? = null,
    val numberOfSeasons: Int? = null,
    val voteCount: Int? = null,
    val firstAirDate: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompanies?>? = null,
    val originalName: String? = null,
    val voteAverage: Double? = null,
    val name: String? = null,
    val episodeRunTime: List<Int?>? = null,
    val lastAirDate: String? = null,
    val status: String? = null
)