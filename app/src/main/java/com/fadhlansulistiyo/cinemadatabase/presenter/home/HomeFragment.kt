package com.fadhlansulistiyo.cinemadatabase.presenter.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.ui.MovieAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.PeopleAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.TvAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val movieAdapter = MovieAdapter()
            binding.rvNowPlaying.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            viewModel.getNowPlaying.observe(viewLifecycleOwner) { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> {
                            binding.progressBarNowPlaying.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.progressBarNowPlaying.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
                            binding.rvNowPlaying.adapter = movieAdapter
                        }

                        is Resource.Error -> {
                            binding.progressBarNowPlaying.visibility = View.GONE
                        }
                    }
                }
            }

            val tvAdapter = TvAdapter()
            binding.rvAiringTodayTv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            viewModel.getAiringTodayTv.observe(viewLifecycleOwner) { tv ->
                if (tv != null) {
                    when (tv) {
                        is Resource.Loading -> {
                            binding.progressBarNowPlaying.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            tvAdapter.submitList(tv.data)
                            binding.rvAiringTodayTv.adapter = tvAdapter
                        }
                        is Resource.Error -> {
                            binding.progressBarNowPlaying.visibility = View.GONE
                        }
                    }
                }
            }

            val peopleAdapter = PeopleAdapter()
            binding.rvTrendingPeople.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            viewModel.getTrendingPeople.observe(viewLifecycleOwner) { people ->
                if (people != null) {
                    when (people) {
                        is Resource.Loading -> {
                            binding.progressBarNowPlaying.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            peopleAdapter.submitList(people.data)
                            Log.d("GetTrendingPeople", people.data.toString())
                            binding.rvTrendingPeople.adapter = peopleAdapter
                        }
                        is Resource.Error -> {
                            binding.progressBarNowPlaying.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}