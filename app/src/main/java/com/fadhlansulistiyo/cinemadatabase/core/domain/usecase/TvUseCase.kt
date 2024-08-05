package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import kotlinx.coroutines.flow.Flow

interface TvUseCase {
    fun getAiringTodayTv(): Flow<Resource<List<Tv>>>
    suspend fun getDetailTv(seriesId: Int): Resource<DetailTv>
    fun getCast(seriesId: Int): Flow<Resource<List<TvCast>>>
}