package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailMovie(
    val id: Int,
    val title: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val runtime: Int? = null,
    val backdropPath: String? = null,
    val revenue: Int? = null,
    val releaseDate: String? = null,
    val genres: List<Genres?>? = null,
    val popularity: Double? = null,
    val voteCount: Int? = null,
    val budget: Int? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompanies?>? = null,
    val voteAverage: Double? = null,
    val status: String? = null,
    val isWatchlist: Boolean? = false
)