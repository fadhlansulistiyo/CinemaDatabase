package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, TvEntity::class, PeopleEntity::class],
    version = 5,
    exportSchema = false
)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun cinemaDao(): CinemaDao
}