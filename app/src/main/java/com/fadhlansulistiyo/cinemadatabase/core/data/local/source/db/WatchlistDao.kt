package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchlist(watchlist: WatchlistEntity)

    @Delete
    suspend fun removeWatchlist(watchlist: WatchlistEntity)

    @Query("SELECT * FROM watchlist WHERE title = :title LIMIT 1")
    suspend fun getWatchlistByTitle(title: String): WatchlistEntity?

    @Query("SELECT * FROM watchlist")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>
}