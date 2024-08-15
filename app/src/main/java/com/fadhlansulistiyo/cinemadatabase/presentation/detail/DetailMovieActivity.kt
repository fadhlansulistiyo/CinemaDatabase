package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovieWithCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.ui.CastAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailMovieBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.loadImageOriginal
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toVoteAverageFormat
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedDateString
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedProductionCompanies
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedRuntime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var castAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        enableEdgeToEdge()
        handleWindowInsets()

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        viewModel.fetchDetailMovie(movieId)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        castAdapter = CastAdapter { castId ->
            val intent = Intent(this, DetailPeopleActivity::class.java).apply {
                putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, castId)
            }
            startActivity(intent)
        }
        binding.detailRecyclerViewCast.adapter = castAdapter
    }

    private fun setupObservers() {
        viewModel.isWatchlist.observe(this) { isWatchlist ->
            setWatchlistState(isWatchlist)
        }

        viewModel.detailMovie.observe(this) { resource ->
            handleDetailMovie(resource)
        }
    }

    private fun handleDetailMovie(resource: Resource<DetailMovieWithCast>) {
        when (resource) {
            is Resource.Error -> {
                showLoading(false)
                showToast(resource.message.toString())
                binding.errorLayout.visibility = View.VISIBLE
                binding.errorMsg.textError.text = resource.message
            }

            is Resource.Loading -> {
                showLoading(true)
            }

            is Resource.Success -> {
                showLoading(false)
                binding.layoutMain.visibility = View.VISIBLE
                resource.data?.let {
                    setDetailMovie(it.detail)
                    castAdapter.submitList(it.cast)
                }
            }

            else -> {}
        }
    }

    private fun setDetailMovie(detailMovie: DetailMovie) {
        binding.apply {
            detailPosterPath.loadImageOriginal(this@DetailMovieActivity, detailMovie.posterPath)
            detailBackdropPath.loadImageOriginal(this@DetailMovieActivity, detailMovie.backdropPath)
            detailTitle.text = detailMovie.title
            detailOverview.text = detailMovie.overview
            detailRuntime.text = detailMovie.runtime.toFormattedRuntime()
            detailReleaseDate.text = detailMovie.releaseDate.toFormattedDateString()
            detailGenres.text = detailMovie.genres.joinToString(", ") { it.name }
            detailCompanies.text = detailMovie.productionCompanies.map { it.name }
                .toFormattedProductionCompanies()
            detailVoteAverage.text = detailMovie.voteAverage.toVoteAverageFormat(1)
        }
    }


    private fun setupListeners() {
        binding.btnWatchlist.setOnClickListener {
            val currentDetail =
                viewModel.detailMovie.value?.data?.detail ?: return@setOnClickListener
            viewModel.toggleWatchlistMovie(
                WatchlistMovie(
                    id = currentDetail.id,
                    title = currentDetail.title,
                    posterPath = currentDetail.posterPath,
                    releaseDate = currentDetail.releaseDate,
                    voteAverage = currentDetail.voteAverage
                )
            )
        }

        @Suppress("DEPRECATION")
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setWatchlistState(isWatchlist: Boolean) {
        binding.btnWatchlist.setImageResource(
            if (isWatchlist) R.drawable.baseline_watchlist_filled else R.drawable.baseline_watchlist
        )
    }

    private fun setupBinding() {
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}