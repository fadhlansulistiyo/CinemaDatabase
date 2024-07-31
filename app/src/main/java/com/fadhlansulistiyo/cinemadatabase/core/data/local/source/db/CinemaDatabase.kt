package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.WatchlistEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model.TvEntity

@Database(
    entities = [MovieEntity::class, TvEntity::class, PeopleEntity::class, WatchlistEntity::class],
    version = 8,
    exportSchema = false
)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun peopleDao(): PeopleDao
    abstract fun watchlistDao(): WatchlistDao
}