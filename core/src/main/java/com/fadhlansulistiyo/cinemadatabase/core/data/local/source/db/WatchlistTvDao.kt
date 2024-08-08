package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistTvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistTvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchlist(watchlist: WatchlistTvEntity)

    @Delete
    suspend fun removeWatchlist(watchlist: WatchlistTvEntity)

    @Query("SELECT * FROM watchlist_tv WHERE name = :name LIMIT 1")
    suspend fun getWatchlistByTitle(name: String): WatchlistTvEntity?

    @Query("SELECT * FROM watchlist_tv")
    fun getAllWatchlist(): Flow<List<WatchlistTvEntity>>
}