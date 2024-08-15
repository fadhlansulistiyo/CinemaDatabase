package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailTvWithCast(
    val detail: DetailTv,
    val cast: List<TvCast>
)