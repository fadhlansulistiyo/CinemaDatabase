package com.fadhlansulistiyo.cinemadatabase.presentation.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistMovieAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistTvAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentWatchlistMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistMovieTvFragment : Fragment() {

    private var _binding: FragmentWatchlistMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchlistMovieTvViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_POSITION, 0)

        if (index == 1) {
            viewModel.getWatchlistMovies.observe(viewLifecycleOwner) { movies ->
                val adapter = WatchlistMovieAdapter()
                binding.rvMovies.adapter = adapter
                adapter.submitList(movies)
            }
        } else {
            viewModel.getWatchlistTv.observe(viewLifecycleOwner) { tv ->
                val adapter = WatchlistTvAdapter()
                binding.rvMovies.adapter = adapter
                adapter.submitList(tv)
            }
        }
    }

    companion object {
        const val ARG_POSITION = "position"
    }
}