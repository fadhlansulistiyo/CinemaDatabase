package com.fadhlansulistiyo.cinemadatabase.core.domain.usecase

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface TvUseCase {
    fun getAiringTodayTv(): Flow<Resource<List<Tv>>>
}