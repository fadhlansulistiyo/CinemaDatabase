package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun cinemaDao(): CinemaDao
}