package com.fadhlansulistiyo.cinemadatabase.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentSearchBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.SearchUtils

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        val setUpSearch = SearchUtils()
        setUpSearch.setUpSearchBar(requireActivity(), binding.searchBar)
        setUpSearch.setUpSearchView(requireActivity() as AppCompatActivity, binding.searchBar, binding.searchView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}