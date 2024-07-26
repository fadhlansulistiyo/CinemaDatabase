package com.fadhlansulistiyo.cinemadatabase.di

import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideTvUseCase(tvInteractor: TvInteractor): TvUseCase

    @Binds
    @ViewModelScoped
    abstract fun providePeopleUseCase(peopleInteractor: PeopleInteractor): PeopleUseCase
}