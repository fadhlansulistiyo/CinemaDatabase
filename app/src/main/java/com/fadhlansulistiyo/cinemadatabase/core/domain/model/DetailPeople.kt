package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailPeople(
    val id: Int,
    val birthday: String,
    val knownForDepartment: String,
    val profilePath: String,
    val biography: String,
    val placeOfBirth: String,
    val name: String,
)