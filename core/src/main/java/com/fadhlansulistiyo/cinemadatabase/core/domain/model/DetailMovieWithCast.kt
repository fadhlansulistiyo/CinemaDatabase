package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class MovieDetailWithCast(
    val detail: DetailMovie,
    val cast: List<MovieCast>
)