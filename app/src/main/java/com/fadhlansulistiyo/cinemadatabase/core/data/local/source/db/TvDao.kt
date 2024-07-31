package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {
    @Query("SELECT * FROM tv")
    fun getAllTv(): Flow<List<TvEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tv: List<TvEntity>)
}