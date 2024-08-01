package com.fadhlansulistiyo.cinemadatabase.presentation.watchlist.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistMovieAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistTvAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentWatchlistMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistMovieFragment : Fragment() {

    private var _binding: FragmentWatchlistMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchlistMovieViewModel by viewModels()

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

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMovies.layoutManager = layoutManager

        if (index == 1) {
            viewModel.getWatchlistMovies.observe(viewLifecycleOwner) { movies ->
                val adapter = WatchlistMovieAdapter()
                adapter.submitList(movies)
                binding.rvMovies.adapter = adapter
            }
        } else {
            viewModel.getWatchlistTv.observe(viewLifecycleOwner) { tv ->
                val adapter = WatchlistTvAdapter()
                adapter.submitList(tv)
                binding.rvMovies.adapter = adapter
            }
        }
    }

    companion object {
        const val ARG_POSITION = "position"
    }
}