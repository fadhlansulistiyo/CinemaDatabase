package com.fadhlansulistiyo.cinemadatabase.core.domain.model

interface Cast {
    val id: Int
    val castId: Int?
    val character: String?
    val name: String?
    val profilePath: String?
}