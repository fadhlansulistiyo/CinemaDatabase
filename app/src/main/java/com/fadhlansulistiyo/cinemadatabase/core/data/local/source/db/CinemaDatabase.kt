package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistMovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.TvEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.WatchlistTvEntity

@Database(
    entities = [
        MovieEntity::class,
        TvEntity::class,
        PeopleEntity::class,
        WatchlistMovieEntity::class,
        WatchlistTvEntity::class
    ],
    version = 11,
    exportSchema = false
)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun peopleDao(): PeopleDao
    abstract fun watchlistMovieDao(): WatchlistMovieDao
    abstract fun watchlistTvDao(): WatchlistTvDao
}