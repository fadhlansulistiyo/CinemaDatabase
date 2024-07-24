package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tv")
    fun getAllTv(): Flow<List<TvEntity>>

    @Query("SELECT * FROM movie where isBookmarked = 1")
    fun getBookmarkedMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tv: List<TvEntity>)

    @Update
    fun updateBookmarkMovie(movie: MovieEntity)
}