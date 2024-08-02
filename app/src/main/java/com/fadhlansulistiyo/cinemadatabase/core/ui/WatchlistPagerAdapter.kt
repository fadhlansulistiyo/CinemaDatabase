package com.fadhlansulistiyo.cinemadatabase.core.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fadhlansulistiyo.cinemadatabase.presentation.watchlist.WatchlistMovieTvFragment

class WatchlistPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = WatchlistMovieTvFragment()
        fragment.arguments = Bundle().apply {
            putInt(WatchlistMovieTvFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }
}