package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class TvCast(
    override val id: Int,
    override val castId: Int,
    override val character: String,
    override val name: String,
    override val profilePath: String
) : Cast