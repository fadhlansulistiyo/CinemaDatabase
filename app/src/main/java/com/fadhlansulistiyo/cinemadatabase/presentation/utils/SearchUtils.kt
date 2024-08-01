package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.fadhlansulistiyo.cinemadatabase.R
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class SearchUtils {
    fun setUpSearchView(
        activity: AppCompatActivity,
        searchBar: SearchBar,
        searchView: SearchView
    ) {
        searchView.inflateMenu(R.menu.option_search)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = searchView.text.toString()
            searchBar.setText(query)
            searchView.hide()

            false
        }
        val onBackPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                searchView.hide()
            }
        }
        activity.onBackPressedDispatcher.addCallback(activity, onBackPressedCallback)
        searchView.addTransitionListener { _, _, newState ->
            onBackPressedCallback.isEnabled = newState == SearchView.TransitionState.SHOWN
        }
    }

    fun setUpSearchBar(activity: Activity, searchBar: SearchBar) {
        searchBar.inflateMenu(R.menu.option_search)
        searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_filter -> {

                }
            }
            true
        }
    }
}