package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.MovieEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.PeopleEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.model.TvEntity

@Database(
    entities = [MovieEntity::class, TvEntity::class, PeopleEntity::class],
    version = 5,
    exportSchema = false
)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun cinemaDao(): CinemaDao
}