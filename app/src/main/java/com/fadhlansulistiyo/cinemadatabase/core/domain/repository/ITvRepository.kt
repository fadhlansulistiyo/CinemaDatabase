package com.fadhlansulistiyo.cinemadatabase.core.domain.repository

import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface ITvRepository {
    fun getAiringTodayTv(): Flow<Resource<List<Tv>>>
}