package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remotesource.response.PeopleResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People

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

    // Map People to PeopleEntity
    fun mapPeopleDomainToEntity(input: People) = PeopleEntity(
        id = input.id,
        name = input.name,
        profilePath = input.profilePath
    )
}