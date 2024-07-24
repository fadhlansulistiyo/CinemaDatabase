package com.fadhlansulistiyo.cinemadatabase.core.di

import android.content.Context
import androidx.room.Room
import com.fadhlansulistiyo.cinemadatabase.core.data.localsource.CinemaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CinemaDatabase = Room.databaseBuilder(
        context,
        CinemaDatabase::class.java, "Cinema.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCinemaDao(database: CinemaDatabase) = database.cinemaDao()

}