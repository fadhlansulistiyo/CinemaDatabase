package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import kotlinx.coroutines.flow.Flow

interface ITvRepository {
    fun getAiringTodayTv(): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<Tv>>>
    suspend fun getDetailTv(seriesId: Int): com.fadhlansulistiyo.cinemadatabase.core.data.Resource<DetailTv>
    fun getCast(seriesId: Int): Flow<com.fadhlansulistiyo.cinemadatabase.core.data.Resource<List<TvCast>>>
}