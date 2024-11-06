package com.fadhlansulistiyo.cinemadatabase.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Movie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.People
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.ui.NowPlayingAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.PeopleAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.TvAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentHomeBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.base.BaseFragment
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailPeopleActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailTvActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.AutoScrollViewPagerHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var tvAdapter: TvAdapter
    private lateinit var peopleAdapter: PeopleAdapter
    private lateinit var autoScrollViewPagerHelper: AutoScrollViewPagerHelper

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        initializeAutoScrollViewPagerHelper()
        setupObservers()
    }

    private fun setupRecyclerViews() {
        nowPlayingAdapter = NowPlayingAdapter { openDetailActivity(DetailMovieActivity::class.java, it) }
        tvAdapter = TvAdapter { openDetailActivity(DetailTvActivity::class.java, it) }
        peopleAdapter = PeopleAdapter { openDetailActivity(DetailPeopleActivity::class.java, it) }
    }

    private fun openDetailActivity(activityClass: Class<*>, itemId: Int) {
        Intent(requireContext(), activityClass).apply {
            putExtra("EXTRA_ID", itemId)
            startActivity(this)
        }
    }

    private fun initializeAutoScrollViewPagerHelper() {
        autoScrollViewPagerHelper = AutoScrollViewPagerHelper(binding.viewPagerNowPlaying, 3000L, 200L)
    }

    private fun setupObservers() {
        viewModel.getNowPlaying.observe(viewLifecycleOwner, ::handleNowPlayingResource)
        viewModel.getAiringTodayTv.observe(viewLifecycleOwner, ::handleAiringTodayTvResource)
        viewModel.getTrendingPeople.observe(viewLifecycleOwner, ::handleTrendingPeopleResource)
    }

    private fun handleNowPlayingResource(resource: Resource<List<Movie>>) {
        when (resource) {
            is Resource.Loading -> showNowPlayingLoading()
            is Resource.Success -> showNowPlayingMovies(resource.data ?: emptyList())
            is Resource.Error -> showNowPlayingError()
        }
    }

    private fun handleAiringTodayTvResource(resource: Resource<List<Tv>>) {
        when (resource) {
            is Resource.Loading -> showAiringTodayLoading()
            is Resource.Success -> showAiringTodayTv(resource.data ?: emptyList())
            is Resource.Error -> showAiringTodayError()
        }
    }

    private fun handleTrendingPeopleResource(resource: Resource<List<People>>) {
        when (resource) {
            is Resource.Loading -> showTrendingPeopleLoading()
            is Resource.Success -> showTrendingPeople(resource.data ?: emptyList())
            is Resource.Error -> showTrendingPeopleError()
        }
    }

    private fun showNowPlayingLoading() {
        binding.progressBarNowPlaying.visibility = View.VISIBLE
        binding.viewPagerNowPlaying.visibility = View.GONE
    }

    private fun showNowPlayingMovies(data: List<Movie>) {
        binding.progressBarNowPlaying.visibility = View.GONE
        binding.viewPagerNowPlaying.visibility = View.VISIBLE
        nowPlayingAdapter.submitList(data)
        binding.viewPagerNowPlaying.adapter = nowPlayingAdapter
        autoScrollViewPagerHelper.startAutoScroll()
    }

    private fun showNowPlayingError() {
        binding.progressBarNowPlaying.visibility = View.GONE
        binding.viewPagerNowPlaying.visibility = View.GONE
    }

    private fun showAiringTodayLoading() {
        binding.progressBarAiringTodayTv.visibility = View.VISIBLE
        binding.rvAiringTodayTv.visibility = View.GONE
    }

    private fun showAiringTodayTv(data: List<Tv>) {
        binding.progressBarAiringTodayTv.visibility = View.GONE
        binding.rvAiringTodayTv.visibility = View.VISIBLE
        tvAdapter.submitList(data)
        binding.rvAiringTodayTv.adapter = tvAdapter
    }

    private fun showAiringTodayError() {
        binding.progressBarAiringTodayTv.visibility = View.GONE
        binding.rvAiringTodayTv.visibility = View.GONE
    }

    private fun showTrendingPeopleLoading() {
        binding.progressBarTrendingPeople.visibility = View.VISIBLE
        binding.rvTrendingPeople.visibility = View.GONE
    }

    private fun showTrendingPeople(data: List<People>) {
        binding.progressBarTrendingPeople.visibility = View.GONE
        binding.rvTrendingPeople.visibility = View.VISIBLE
        peopleAdapter.submitList(data)
        binding.rvTrendingPeople.adapter = peopleAdapter
    }

    private fun showTrendingPeopleError() {
        binding.progressBarTrendingPeople.visibility = View.GONE
        binding.rvTrendingPeople.visibility = View.GONE
    }

    override fun onDestroyView() {
        binding.viewPagerNowPlaying.adapter = null
        binding.rvAiringTodayTv.adapter = null
        binding.rvTrendingPeople.adapter = null
        binding.main.removeAllViews()
        autoScrollViewPagerHelper.stopAutoScroll()
        super.onDestroyView()
    }
}
