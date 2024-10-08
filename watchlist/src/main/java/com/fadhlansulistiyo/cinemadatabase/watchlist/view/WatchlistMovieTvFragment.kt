package com.fadhlansulistiyo.cinemadatabase.watchlist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistMovieAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistTvAdapter
import com.fadhlansulistiyo.cinemadatabase.di.WatchlistModuleDependencies
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailTvActivity
import com.fadhlansulistiyo.cinemadatabase.watchlist.databinding.FragmentWatchlistMovieBinding
import com.fadhlansulistiyo.cinemadatabase.watchlist.di.DaggerWatchlistComponent
import com.fadhlansulistiyo.cinemadatabase.watchlist.di.ViewModelFactory
import com.fadhlansulistiyo.cinemadatabase.watchlist.viewmodel.WatchlistMovieTvViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class WatchlistMovieTvFragment : Fragment() {

    private var _binding: FragmentWatchlistMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: WatchlistMovieAdapter
    private lateinit var tvAdapter: WatchlistTvAdapter

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: WatchlistMovieTvViewModel by viewModels {
        factory
    }

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
        setRecyclerView()
        observeWatchlist(index)
    }

    private fun observeWatchlist(index: Int?) {
        if (index == 1) {
            viewModel.getWatchlistMovies.observe(viewLifecycleOwner) { movies ->
                movieAdapter.submitList(movies)
                binding.rvMovies.adapter = movieAdapter
                toggleEmptyWatchlistLayout(movies.isEmpty())
            }
        } else {
            viewModel.getWatchlistTv.observe(viewLifecycleOwner) { tv ->
                tvAdapter.submitList(tv)
                binding.rvMovies.adapter = tvAdapter
                toggleEmptyWatchlistLayout(tv.isEmpty())
            }
        }
    }

    private fun setRecyclerView() {
        movieAdapter = WatchlistMovieAdapter {
            Intent(activity, DetailMovieActivity::class.java).apply {
                putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it)
                startActivity(this)
            }
        }
        tvAdapter = WatchlistTvAdapter {
            Intent(activity, DetailTvActivity::class.java).apply {
                putExtra(DetailTvActivity.EXTRA_TV_ID, it)
                startActivity(this)
            }
        }
    }

    private fun toggleEmptyWatchlistLayout(isEmpty: Boolean) {
        if (isEmpty) {
            binding.layoutEmptyWatchlist.visibility = View.VISIBLE
            binding.rvMovies.visibility = View.GONE
        } else {
            binding.layoutEmptyWatchlist.visibility = View.GONE
            binding.rvMovies.visibility = View.VISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerWatchlistComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    WatchlistModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    companion object {
        const val ARG_POSITION = "position"
    }
}