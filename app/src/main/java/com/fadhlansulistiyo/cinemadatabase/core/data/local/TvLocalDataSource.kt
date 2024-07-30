package com.fadhlansulistiyo.cinemadatabase.core.data.local

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db.TvDao
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.TvEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvLocalDataSource @Inject constructor(private val tvDao: TvDao) {

    fun getAllTv(): Flow<List<TvEntity>> = tvDao.getAllTv()

    suspend fun insertTv(tv: List<TvEntity>) = tvDao.insertTv(tv)

    fun getWatchlistTv(): Flow<List<TvEntity>> = tvDao.getWatchlistTv()

    fun setWatchlistTv(tv: TvEntity, newState: Boolean) {
        tv.isWatchlist = newState
        tvDao.updateWatchlistTv(tv)
    }

}