package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import androidx.paging.PagingData
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeopleWithCredits
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.PopularPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IPeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleInteractor @Inject constructor(private val peopleRepository: IPeopleRepository) : PeopleUseCase {
    override fun getTrendingPeople(): Flow<Resource<List<People>>> {
        return peopleRepository.getTrendingPeople()
    }

    override suspend fun getDetailPeople(peopleId: Int): Resource<DetailPeopleWithCredits> {
        return peopleRepository.getDetailPeople(peopleId)
    }

    override fun getPopularPeople(): Flow<PagingData<PopularPeople>> {
        return peopleRepository.getPopularPeople()
    }
}