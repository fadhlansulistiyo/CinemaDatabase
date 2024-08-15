package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailMovieWithCast(
    val detail: DetailMovie,
    val cast: List<MovieCast>
)