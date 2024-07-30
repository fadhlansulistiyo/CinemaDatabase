package com.fadhlansulistiyo.cinemadatabase.core.data.local

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db.PeopleDao
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.PeopleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleLocalDataSource @Inject constructor(private val peopleDao: PeopleDao) {

    fun getAllPeople(): Flow<List<PeopleEntity>> = peopleDao.getAllPeople()

    suspend fun insertPeople(people: List<PeopleEntity>) = peopleDao.insertPeople(people)

}