package com.fadhlansulistiyo.cinemadatabase.core.di

import com.fadhlansulistiyo.cinemadatabase.core.data.CinemaRepository
import com.fadhlansulistiyo.cinemadatabase.core.domain.ICinemaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(cinemaRepository: CinemaRepository): ICinemaRepository
}