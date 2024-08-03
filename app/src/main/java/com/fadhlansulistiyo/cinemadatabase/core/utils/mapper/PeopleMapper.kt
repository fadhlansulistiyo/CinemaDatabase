package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailPeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_NOT_YET_AVAILABLE

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
            gender = input.gender ?: 0,
            knownForDepartment = input.knownForDepartment ?: DATA_NOT_YET_AVAILABLE,
            profilePath = input.profilePath ?: "",
            biography = input.biography ?: DATA_NOT_YET_AVAILABLE,
            placeOfBirth = input.placeOfBirth ?: DATA_NOT_YET_AVAILABLE,
        )
    }
}