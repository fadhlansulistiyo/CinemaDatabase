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
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        viewModel.fetchMovieDetail(movieId)

        viewModel.isWatchlist.observe(this) { isWatchlist ->
            setFavoriteState(isWatchlist)
        }

        viewModel.movieDetail.observe(this) { detailMovie ->
            when (detailMovie) {
                is Resource.Error -> {
                    binding.progressBar.hide()
                    Log.e("DetailMovieActivity", "Error: ${detailMovie.message}")
                }

                is Resource.Loading -> {
                    binding.progressBar.show()
                    Log.d("DetailMovieActivity", "Loading")
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    Log.d("DetailMovieActivity", "Success: ${detailMovie.data}")
                    detailMovie.data?.let { setDetailMovie(it) }
                }
            }
        }

        binding.fab.setOnClickListener {
            val currentDetail = viewModel.movieDetail.value?.data ?: return@setOnClickListener
            viewModel.setUserFavorite(
                WatchlistMovie(
                    id = currentDetail.id,
                    title = currentDetail.title.toString(),
                    posterPath = currentDetail.posterPath.toString(),
                    releaseDate = currentDetail.releaseDate.toString(),
                    voteAverage = currentDetail.voteAverage!!
                )
            )
        }
    }

    private fun setDetailMovie(detailMovie: DetailMovie) {
        binding.apply {
            Glide.with(this@DetailMovieActivity)
                .load(IMAGE_URL + detailMovie.posterPath)
                .into(posterImageView)
            titleTextView.text = detailMovie.title
            originalTitleTextView.text = detailMovie.originalTitle
            overviewTextView.text = detailMovie.overview
            runtimeTextView.text = detailMovie.runtime.toString()
            revenueTextView.text = detailMovie.revenue.toString()
            releaseDateTextView.text = detailMovie.releaseDate
            genresTextView.text = detailMovie.genres?.joinToString(", ") { it?.name ?: "" }
            popularityTextView.text = detailMovie.popularity.toString()
            voteCountTextView.text = detailMovie.voteCount.toString()
            budgetTextView.text = detailMovie.budget.toString()
            productionCompaniesTextView.text =
                detailMovie.productionCompanies?.joinToString(", ") { it?.name ?: "" }
            voteAverageTextView.text = detailMovie.voteAverage.toString()
            statusTextView.text = detailMovie.status
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
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}