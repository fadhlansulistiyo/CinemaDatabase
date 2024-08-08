package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ITvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvInteractor @Inject constructor(private val tvRepository: ITvRepository) : TvUseCase {

    override fun getAiringTodayTv(): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<Tv>>> {
        return tvRepository.getAiringTodayTv()
    }

    override suspend fun getDetailTv(seriesId: Int): com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailTv> {
        return tvRepository.getDetailTv(seriesId)
    }

    override fun getCast(seriesId: Int): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<TvCast>>> {
        return tvRepository.getCast(seriesId)
    }

}