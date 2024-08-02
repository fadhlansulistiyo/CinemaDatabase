package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.fadhlansulistiyo.cinemadatabase.R
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class SearchUtils {
    fun setUpSearchView(
        activity: AppCompatActivity,
        searchBar: SearchBar,
        searchView: SearchView,
        loadQuery: (query: String) -> Unit
    ) {
        searchView.inflateMenu(R.menu.option_search)
        searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = searchView.text.toString()
            searchBar.setText(query)
            searchView.hide()

            loadQuery(query)
            false
        }
        searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchBar.setText(query)
                loadQuery(query)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
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