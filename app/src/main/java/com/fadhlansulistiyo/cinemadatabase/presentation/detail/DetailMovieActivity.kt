package com.fadhlansulistiyo.cinemadatabase.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.MovieCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailMovie
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistMovie
import com.fadhlansulistiyo.cinemadatabase.core.ui.CastAdapter
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL_ORIGINAL
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailMovieBinding
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
        viewModel.fetchMovieDetail(movieId)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        castAdapter = CastAdapter()
        binding.detailRecyclerViewCast.adapter = castAdapter
    }

    private fun setupObservers() {
        viewModel.isWatchlist.observe(this) { isWatchlist ->
            setWatchlistState(isWatchlist)
        }

        viewModel.movieDetail.observe(this) { detailMovie ->
            handleMovieDetail(detailMovie)
        }

        viewModel.movieCast.observe(this) { castResource ->
            handleCastResource(castResource)
        }
    }

    private fun handleMovieDetail(detailMovie: Resource<DetailMovie>) {
        when (detailMovie) {
            is Resource.Error -> {
                binding.progressBar.visibility = View.GONE
                showToast(detailMovie.message.toString())
            }

            is Resource.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Resource.Success -> {
                binding.progressBar.visibility = View.GONE
                detailMovie.data?.let { setDetailMovie(it) }
            }
        }
    }

    private fun handleCastResource(movieCastResource: Resource<List<MovieCast>>) {
        when (movieCastResource) {
            is Resource.Error -> {
                binding.progressBarCast.visibility = View.GONE
                showToast(movieCastResource.message.toString())
                Log.e("GetCastMovieActivity", "Error: ${movieCastResource.message}")
            }

            is Resource.Loading -> {
                binding.progressBarCast.visibility = View.VISIBLE
            }

            is Resource.Success -> {
                Log.d("GetCastMovieActivity", "Cast data: ${movieCastResource.data}")
                binding.progressBarCast.visibility = View.GONE
                castAdapter.submitList(movieCastResource.data)
            }
        }
    }

    private fun setupListeners() {
        binding.btnWatchlist.setOnClickListener {
            val currentDetail = viewModel.movieDetail.value?.data ?: return@setOnClickListener
            viewModel.toggleWatchlistMovie(
                WatchlistMovie(
                    id = currentDetail.id,
                    title = currentDetail.title.toString(),
                    posterPath = currentDetail.posterPath.toString(),
                    releaseDate = currentDetail.releaseDate.toString(),
                    voteAverage = currentDetail.voteAverage ?: 0.0
                )
            )
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setDetailMovie(detailMovie: DetailMovie) {
        binding.apply {
            Glide.with(this@DetailMovieActivity)
                .load(IMAGE_URL_ORIGINAL + detailMovie.backdropPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(detailBackdropPath)

            Glide.with(this@DetailMovieActivity)
                .load(IMAGE_URL_ORIGINAL + detailMovie.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(detailPosterPath)
            detailTitle.text = detailMovie.title ?: ""
            detailOverview.text = detailMovie.overview ?: ""
            detailRuntime.text = detailMovie.runtime?.toFormattedRuntime()
            detailReleaseDate.text = detailMovie.releaseDate?.toFormattedDateString()
            detailGenres.text = detailMovie.genres?.joinToString(", ") { it?.name ?: "" }
            detailCompanies.text = detailMovie.productionCompanies?.map { it?.name }?.toFormattedProductionCompanies()
            detailVoteAverage.text = detailMovie.voteAverage?.toVoteAverageFormat(1)
        }
    }

    private fun setWatchlistState(isWatchlist: Boolean) {
        if (isWatchlist) {
            binding.btnWatchlist.setImageResource(R.drawable.baseline_watchlist_filled)
        } else {
            binding.btnWatchlist.setImageResource(R.drawable.baseline_watchlist)
        }
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