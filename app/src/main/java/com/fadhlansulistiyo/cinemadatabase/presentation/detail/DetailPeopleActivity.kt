package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MultiCreditsMovieTv
import com.fadhlansulistiyo.cinemadatabase.core.ui.MultiCreditsAdapter
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.IMAGE_URL_ORIGINAL
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailPeopleBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedDateString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeopleActivity : AppCompatActivity() {

    private var _binding: ActivityDetailPeopleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailPeopleViewModel by viewModels()
    private lateinit var creditsAdapter: MultiCreditsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        enableEdgeToEdge()
        handleWindowInsets()
        fetchPeopleDetail()
        setupCreditsAdapter()
        setupObservers()
        setupListeners()
    }

    private fun setupCreditsAdapter() {
        creditsAdapter = MultiCreditsAdapter { item ->
            val intent = when (item.mediaType) {
                "movie" -> Intent(this, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, item.id)
                }
                "tv" -> Intent(this, DetailTvActivity::class.java).apply {
                    putExtra(DetailTvActivity.EXTRA_TV_ID, item.id)
                }
                else -> null
            }
            intent?.let { startActivity(it) }
        }
        binding.detailRecyclerViewMovie.adapter = creditsAdapter
        binding.detailRecyclerViewMovie.setHasFixedSize(true)
    }

    private fun fetchPeopleDetail() {
        val peopleId = intent.getIntExtra(EXTRA_PEOPLE_ID, 0)
        viewModel.fetchPeopleDetail(peopleId)
    }

    private fun setupObservers() {
        viewModel.peopleDetail.observe(this) { handlePeopleDetail(it) }
        viewModel.credits.observe(this) { handleCredits(it) }
    }

    private fun handlePeopleDetail(detailPeople: Resource<DetailPeople>) {
        when (detailPeople) {
            is Resource.Error -> showLoading(false)
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                showLoading(false)
                detailPeople.data?.let { setDetailPeople(it) }
            }
        }
    }

    private fun handleCredits(credits: Resource<List<MultiCreditsMovieTv>>) {
        when (credits) {
            is Resource.Error -> showLoading(false)
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                showLoading(false)
                creditsAdapter.submitList(credits.data)
            }
        }
    }

    private fun setDetailPeople(detailPeople: DetailPeople) {
        binding.apply {
            Glide.with(this@DetailPeopleActivity)
                .load(IMAGE_URL_ORIGINAL + detailPeople.profilePath)
                .into(detailProfilePath)
            Glide.with(this@DetailPeopleActivity)
                .load(IMAGE_URL + detailPeople.profilePath)
                .into(detailBackdropPath)

            detailName.text = detailPeople.name
            itemKnownFor.text = detailPeople.knownForDepartment
            itemBirthDay.text = detailPeople.birthday.toFormattedDateString()
            itemPlaceOfBirth.text = detailPeople.placeOfBirth
            itemBiography.text = detailPeople.biography
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupBinding() {
        _binding = ActivityDetailPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @Suppress("DEPRECATION")
    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_PEOPLE_ID = "extra_people_id"
    }
}
