package com.fadhlansulistiyo.cinemadatabase.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fadhlansulistiyo.cinemadatabase.core.ui.SearchResultAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentSearchBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailTvActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchResultAdapter: SearchResultAdapter

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchViewModel.setSearchQuery(query)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        searchResultAdapter = SearchResultAdapter { item ->
            when (item.mediaType) {
                "movie" -> {
                    val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, item.id)
                    startActivity(intent)
                }

                "tv" -> {
                    val intent = Intent(requireContext(), DetailTvActivity::class.java)
                    intent.putExtra(DetailTvActivity.EXTRA_TV_ID, item.id)
                    startActivity(intent)
                }

                null -> return@SearchResultAdapter
            }
        }

        binding.recyclerView.adapter = searchResultAdapter

        searchViewModel.searchResults.observe(viewLifecycleOwner) {
            searchResultAdapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}