package com.fadhlansulistiyo.cinemadatabase.core.di

import android.content.Context
import androidx.room.Room
import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.db.CinemaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CinemaDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("cinemadatabase".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            CinemaDatabase::class.java, "Cinema.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMovieDao(database: CinemaDatabase) = database.movieDao()

    @Provides
    fun provideTvDao(database: CinemaDatabase) = database.tvDao()

    @Provides
    fun providePeopleDao(database: CinemaDatabase) = database.peopleDao()

    @Provides
    fun provideWatchlistMovieDao(database: CinemaDatabase) = database.watchlistMovieDao()

    @Provides
    fun provideWatchlistTvDao(database: CinemaDatabase) = database.watchlistTvDao()
}