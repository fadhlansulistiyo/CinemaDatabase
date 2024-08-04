package com.fadhlansulistiyo.cinemadatabase.core.domain.model

import com.google.gson.annotations.SerializedName

data class Cast(
    val id: Int,
    val castId: Int,
    val character: String? = null,
    val name: String? = null,
    val profilePath: String? = null,
)