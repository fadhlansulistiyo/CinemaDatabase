package com.fadhlansulistiyo.cinemadatabase.presentation.people

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.fadhlansulistiyo.cinemadatabase.core.ui.LoadingStateAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.PopularPeopleAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentPeopleBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailPeopleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private val peopleViewModel: PeopleViewModel by viewModels()
    private lateinit var adapter: PopularPeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = PopularPeopleAdapter {
            Intent(activity, DetailPeopleActivity::class.java).apply {
                putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, it)
                startActivity(this)
            }
        }
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    showLoading(true)
                }

                is LoadState.NotLoading -> {
                    showLoading(false)
                }

                is LoadState.Error -> {
                    showLoading(false)
                    val errorState = loadState.refresh as LoadState.Error
                    showToast(errorState.error.message.toString())
                }
            }
        }
    }

    private fun setupObservers() {
        peopleViewModel.popularPeople.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}