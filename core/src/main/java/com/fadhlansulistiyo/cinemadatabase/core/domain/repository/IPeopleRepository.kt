package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import kotlinx.coroutines.flow.Flow

interface IPeopleRepository {
    fun getTrendingPeople(): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<People>>>
    suspend fun getDetailPeople(peopleId: Int): com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailPeople>
    fun getPopularPeople(): Flow<PagingData<PopularPeople>>
    fun getCredits(id: Int): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<MultiCreditsMovieTv>>>
}