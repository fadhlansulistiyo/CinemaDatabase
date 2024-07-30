package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import kotlinx.coroutines.flow.Flow

interface PeopleUseCase {
    fun getTrendingPeople(): Flow<Resource<List<People>>>
    suspend fun getDetailPeople(peopleId: Int): Resource<DetailPeople>
}