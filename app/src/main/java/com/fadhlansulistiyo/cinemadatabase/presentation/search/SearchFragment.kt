package com.fadhlansulistiyo.cinemadatabase.presentation.search

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.ui.SearchResultAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentSearchBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailMovieActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailPeopleActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.detail.DetailTvActivity
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.SearchUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchViewModel.setSearchQuery(query)
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
                "person" -> {
                    val intent = Intent(requireContext(), DetailPeopleActivity::class.java)
                    intent.putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, item.id)
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