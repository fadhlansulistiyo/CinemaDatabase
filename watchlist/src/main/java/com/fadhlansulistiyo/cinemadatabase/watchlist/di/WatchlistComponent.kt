package com.fadhlansulistiyo.cinemadatabase.watchlist.di

import android.content.Context
import com.fadhlansulistiyo.cinemadatabase.di.WatchlistModuleDependencies
import com.fadhlansulistiyo.cinemadatabase.watchlist.view.WatchlistMovieTvFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [WatchlistModuleDependencies::class])
interface WatchlistComponent {

    fun inject(fragment: WatchlistMovieTvFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(watchlistModuleDependencies: WatchlistModuleDependencies): Builder
        fun build(): WatchlistComponent
    }

}