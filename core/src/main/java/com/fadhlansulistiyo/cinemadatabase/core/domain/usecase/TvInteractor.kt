package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTvWithCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ITvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvInteractor @Inject constructor(private val tvRepository: ITvRepository) : TvUseCase {

    override fun getAiringTodayTv(): Flow<Resource<List<Tv>>> {
        return tvRepository.getAiringTodayTv()
    }

    override suspend fun getDetailTv(tvId: Int): Resource<DetailTvWithCast> {
        return tvRepository.getDetailTv(tvId)
    }

}