package com.fadhlansulistiyo.cinemadatabase.di

import com.fadhlansulistiyo.cinemadatabase.core.domain.CinemaInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.CinemaUseCase
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
    abstract fun provideCinemaUseCase(cinemaInteractor: CinemaInteractor): CinemaUseCase
}