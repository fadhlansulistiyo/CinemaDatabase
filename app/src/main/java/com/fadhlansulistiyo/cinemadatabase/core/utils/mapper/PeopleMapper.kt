package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.MultiCreditsMovieTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_NOT_YET_AVAILABLE
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

    // Map PeopleEntity to People
    fun mapPeopleEntitiesToDomain(input: List<PeopleEntity>): List<People> =
        input.map {
            People(
                id = it.id,
                name = it.name,
                profilePath = it.profilePath
            )
        }

    // Map DetailPeopleResponse to DetailPeople
    fun mapDetailPeopleResponseToDomain(input: DetailPeopleResponse): DetailPeople {
        return DetailPeople(
            id = input.id,
            name = input.name ?: DATA_NOT_YET_AVAILABLE,
            birthday = input.birthday ?: DATA_NOT_YET_AVAILABLE,
            knownForDepartment = input.knownForDepartment ?: DATA_NOT_YET_AVAILABLE,
            profilePath = input.profilePath ?: "",
            biography = input.biography ?: DATA_NOT_YET_AVAILABLE,
            placeOfBirth = input.placeOfBirth ?: DATA_NOT_YET_AVAILABLE,
        )
    }

    fun mapMultiCreditsResponseToDomain(input: MultiCreditsMovieTvResponse): MultiCreditsMovieTv {
        val releaseDate = input.releaseDate?.takeIf { it.isNotEmpty() }?.let {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(it)
            date?.let { outputFormat.format(date) } ?: it
        }

        return MultiCreditsMovieTv(
            id = input.id,
            posterPath = input.posterPath ?: "",
            mediaType = input.mediaType ?: DATA_NOT_YET_AVAILABLE,
            voteAverage = input.voteAverage ?: 0.0,
            title = input.title ?: DATA_NOT_YET_AVAILABLE,
            releaseDate = releaseDate
        )
    }

}