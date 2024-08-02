package com.fadhlansulistiyo.cinemadatabase.presentation.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.ui.WatchlistPagerAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.FragmentWatchlistBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setUp viewPager2 & tabLayout
        setUpSectionsPager()
    }

    private fun setUpSectionsPager() {
        val sectionsPagerAdapter = WatchlistPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}