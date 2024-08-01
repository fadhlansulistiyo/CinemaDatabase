package com.fadhlansulistiyo.cinemadatabase.di

import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.MovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.PeopleUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.SearchInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.SearchUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.TvUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistMovieUseCase
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvInteractor
import com.fadhlansulistiyo.cinemadatabase.core.domain.usecase.WatchlistTvUseCase
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

    @Binds
    @ViewModelScoped
    abstract fun provideWatchlistMovieUseCase(watchlistInteractor: WatchlistMovieInteractor): WatchlistMovieUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideWatchlistTvUseCase(watchlistInteractor: WatchlistTvInteractor): WatchlistTvUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideSearchUseCase(searchInteractor: SearchInteractor): SearchUseCase
}