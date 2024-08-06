package com.fadhlansulistiyo.cinemadatabase.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.fadhlansulistiyo.cinemadatabase.core.ui.LoadingStateAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.SearchResultAdapter
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.NOT_FOUND
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentSearchBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailTvActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchEditText()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchViewModel.setSearchQuery(query)
                toggleViews(query.isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupRecyclerView() {
        searchResultAdapter = SearchResultAdapter { item ->
            when (item.mediaType) {
                "movie" -> startActivity(Intent(requireContext(), DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, item.id)
                })
                "tv" -> startActivity(Intent(requireContext(), DetailTvActivity::class.java).apply {
                    putExtra(DetailTvActivity.EXTRA_TV_ID, item.id)
                })
                else -> {}
            }
        }

        binding.recyclerView.adapter = searchResultAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { searchResultAdapter.retry() }
        )

        searchResultAdapter.addLoadStateListener { loadState ->
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

    private fun toggleViews(isSearching: Boolean) {
        binding.exploreMovieTv.visibility = if (isSearching) View.GONE else View.VISIBLE
        binding.recyclerView.visibility = if (isSearching) View.VISIBLE else View.GONE
    }

    private fun setupObservers() {
        searchViewModel.searchResults.observe(viewLifecycleOwner) {
            searchResultAdapter.submitData(lifecycle, it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
