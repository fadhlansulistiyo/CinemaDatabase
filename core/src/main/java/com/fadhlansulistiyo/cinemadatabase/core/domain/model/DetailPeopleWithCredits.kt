package com.fadhlansulistiyo.cinemadatabase.core.domain.model

data class DetailPeopleWithCredits(
    val detail: DetailPeople,
    val credits: List<MultiCreditsMovieTv>
)