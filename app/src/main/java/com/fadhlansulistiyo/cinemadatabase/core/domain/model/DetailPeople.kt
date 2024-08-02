package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailPeople(
    val id: Int,
    val birthday: String? = null,
    val gender: Int? = null,
    val knownForDepartment: String? = null,
    val profilePath: String? = null,
    val biography: String? = null,
    val placeOfBirth: String? = null,
    val name: String? = null,
)