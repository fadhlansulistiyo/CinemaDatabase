package com.fadhlansulistiyo.cinemadatabase.core.di

import com.fadhlansulistiyo.cinemadatabase.core.data.repository.MovieRepository
import com.fadhlansulistiyo.cinemadatabase.core.data.repository.PeopleRepository
import com.fadhlansulistiyo.cinemadatabase.core.data.repository.TvRepository
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IMovieRepository
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.IPeopleRepository
import com.fadhlansulistiyo.cinemadatabase.core.domain.repository.ITvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository

    @Binds
    abstract fun provideTvRepository(tvRepository: TvRepository): ITvRepository

    @Binds
    abstract fun providePeopleRepository(peopleRepository: PeopleRepository): IPeopleRepository
}