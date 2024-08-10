package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiCreditsMovieTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.NA
import java.text.SimpleDateFormat
import java.util.Locale

object PeopleMapper {

    // Map PeopleResponse to PeopleEntity
    fun mapPeopleResponsesToEntities(input: List<PeopleResponse>): List<PeopleEntity> {
        val peopleList = ArrayList<PeopleEntity>()
        input.map {
            val people = PeopleEntity(
                id = it.id,
                name = it.name,
                profilePath = it.profilePath

            )
            peopleList.add(people)
        }
        return peopleList
    }

    // Map PeopleEntity to Domain
    fun mapPeopleEntitiesToDomain(input: List<PeopleEntity>): List<People> =
        input.map {
            People(
                id = it.id,
                name = it.name ?: NA,
                profilePath = it.profilePath ?: ""
            )
        }

    // Map DetailPeopleResponse to DetailPeople
    fun mapDetailPeopleResponseToDomain(input: DetailPeopleResponse): DetailPeople {
        return DetailPeople(
            id = input.id,
            name = input.name ?: NA,
            birthday = input.birthday ?: "",
            knownForDepartment = input.knownForDepartment ?: NA,
            profilePath = input.profilePath ?: "",
            biography = input.biography ?: NA,
            placeOfBirth = input.placeOfBirth ?: NA,
        )
    }

    fun mapMultiCreditsResponseToDomain(input: MultiCreditsMovieTvResponse): MultiCreditsMovieTv {
        val releaseDate = input.releaseDate?.takeIf { it.isNotEmpty() }?.let {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(it)
            date?.let { outputFormat.format(date) } ?: it
        } ?: ""

        return MultiCreditsMovieTv(
            id = input.id,
            posterPath = input.posterPath ?: "",
            mediaType = input.mediaType ?: NA,
            voteAverage = input.voteAverage ?: 0.0,
            title = input.title ?: NA,
            releaseDate = releaseDate
        )
    }

    fun mapPeopleResponseToDomain(input: PeopleResponse): PopularPeople {
        return PopularPeople(
            id = input.id,
            name = input.name ?: NA,
            profilePath = input.profilePath ?: ""
        )
    }
}