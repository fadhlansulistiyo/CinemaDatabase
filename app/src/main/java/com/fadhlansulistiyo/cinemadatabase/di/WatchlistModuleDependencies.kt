package com.fadhlansulistiyo.cinemadatabase.di

import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WatchlistModuleDependencies {
    fun watchlistMovieUseCase(): WatchlistMovieUseCase
    fun watchlistTvUseCase(): WatchlistTvUseCase
}