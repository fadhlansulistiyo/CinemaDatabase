package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeopleWithCredits
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import kotlinx.coroutines.flow.Flow

interface IPeopleRepository {
    fun getTrendingPeople(): Flow<Resource<List<People>>>
    suspend fun getDetailPeople(peopleId: Int): Resource<DetailPeopleWithCredits>
    fun getPopularPeople(): Flow<PagingData<PopularPeople>>
}