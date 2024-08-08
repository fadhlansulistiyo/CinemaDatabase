package com.fadhlansulistiyo.cinemadatabase.di

import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WatchlistModule {
    @Binds
    abstract fun provideWatchlistMovieUseCase(watchlistInteractor: WatchlistMovieInteractor): WatchlistMovieUseCase

    @Binds
    abstract fun provideWatchlistTvUseCase(watchlistInteractor: WatchlistTvInteractor): WatchlistTvUseCase
}