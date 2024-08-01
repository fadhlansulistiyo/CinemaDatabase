package com.fadhlansulistiyo.cinemadatabase.presentation.watchlist.tvs

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fadhlansulistiyo.cinemadatabase.R

class WatchlistTvFragment : Fragment() {

    companion object {
        fun newInstance() = WatchlistTvFragment()
    }

    private val viewModel: WatchlistTvViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_watchlist_tv, container, false)
    }
}