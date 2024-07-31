package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailTvBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.model.WatchlistMovieUI
import com.fadhlansulistiyo.cinemadatabase.presentation.model.WatchlistTvUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvActivity : AppCompatActivity() {

    private var _binding: ActivityDetailTvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailTvViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvId = intent.getIntExtra(EXTRA_TV_ID, 0)
        viewModel.fetchTvDetail(tvId)

        viewModel.tvDetail.observe(this) { detailTv ->
            when (detailTv) {
                is Resource.Error -> {
                    binding.progressBar.hide()
                    Log.e("DetailTvActivity", "tvDetail, Error: ${detailTv.message}")
                }

                is Resource.Loading -> {
                    binding.progressBar.show()
                    Log.d("DetailTvActivity", "tvDetail, Loading")
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    Log.d("DetailTvActivity", "tvDetail, Success: ${detailTv.data}")
                    detailTv.data?.let { setDetailTv(it) }
                }
            }
        }

        viewModel.isWatchlist.observe(this) { isWatchlist ->
            setFavoriteState(isWatchlist)
        }

        binding.fab.setOnClickListener {
            val currentDetail = viewModel.tvDetail.value?.data ?: return@setOnClickListener
            viewModel.setUserFavorite(
                WatchlistTvUI(
                    id = currentDetail.id,
                    name = currentDetail.name.toString(),
                    posterPath = currentDetail.posterPath.toString(),
                    firstAirDate = currentDetail.firstAirDate.toString(),
                    voteAverage = currentDetail.voteAverage!!
                )
            )
        }
    }

    private fun setDetailTv(detailTv: DetailTv) {
        binding.apply {
            Glide.with(this@DetailTvActivity)
                .load(IMAGE_URL + detailTv.posterPath)
                .into(posterImageView)
            nameTextView.text = detailTv.name
            originalNameTextView.text = detailTv.originalName
            overviewTextView.text = detailTv.overview
            firstAirDateTextView.text = detailTv.firstAirDate
            lastAirDateTextView.text = detailTv.lastAirDate
            numberOfSeasonsTextView.text = detailTv.numberOfSeasons.toString()
            numberOfEpisodesTextView.text = detailTv.numberOfEpisodes.toString()
            episodeRunTimeTextView.text = detailTv.episodeRunTime?.joinToString(", ") { it?.toString() ?: "" }
            genresTextView.text = detailTv.genres?.joinToString(", ") { it?.name ?: "" }
            popularityTextView.text = detailTv.popularity.toString()
            voteCountTextView.text = detailTv.voteCount.toString()
            voteAverageTextView.text = detailTv.voteAverage.toString()
            statusTextView.text = detailTv.status
            productionCompaniesTextView.text = detailTv.productionCompanies?.joinToString(", ") { it?.name ?: "" }
        }
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fab.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            binding.fab.setImageResource(R.drawable.ic_not_favorite_white_24dp)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_TV_ID = "extra_tv_id"
    }
}