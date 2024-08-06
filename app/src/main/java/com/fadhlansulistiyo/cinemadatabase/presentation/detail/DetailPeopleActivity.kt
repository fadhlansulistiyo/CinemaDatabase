package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.fadhlansulistiyo.cinemadatabase.core.ui.CastAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.MultiCreditsAdapter
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL_ORIGINAL
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
        enableEdgeToEdge()
        _binding = ActivityDetailPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val peopleId = intent.getIntExtra(EXTRA_PEOPLE_ID, 0)
        viewModel.fetchPeopleDetail(peopleId)

        creditsAdapter = MultiCreditsAdapter { item ->
            when (item.mediaType) {
                "movie" -> startActivity(Intent(this, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, item.id)
                })

                "tv" -> startActivity(Intent(this, DetailTvActivity::class.java).apply {
                    putExtra(DetailTvActivity.EXTRA_TV_ID, item.id)
                })

                else -> {}
            }

        }

        viewModel.peopleDetail.observe(this) { detailPeople ->
            when (detailPeople) {
                is Resource.Error -> {
                    showLoading(false)
                    Log.e("DetailPeopleActivity", "peopleDetail, Error: ${detailPeople.message}")
                }

                is Resource.Loading -> {
                    showLoading(true)
                    Log.d("DetailPeopleActivity", "peopleDetail, Loading")
                }

                is Resource.Success -> {
                    showLoading(false)
                    Log.d("DetailPeopleActivity", "peopleDetail, Success: ${detailPeople.data}")
                    detailPeople.data?.let { setDetailPeople(it) }
                }
            }

        }

        viewModel.credits.observe(this) { credits ->
            when (credits) {
                is Resource.Error -> {
                    showLoading(false)
                    Log.e("DetailPeopleActivity", "credits, Error: ${credits.message}")
                }

                is Resource.Loading -> {
                    showLoading(true)
                    Log.d("DetailPeopleActivity", "credits, Loading")
                }

                is Resource.Success -> {
                    showLoading(false)
                    Log.d("DetailPeopleActivity", "credits, Success: ${credits.data}")
                    creditsAdapter.submitList(credits.data)
                    binding.detailRecyclerViewMovie.adapter = creditsAdapter
                }

                else -> {}
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_PEOPLE_ID = "extra_people_id"
    }

}