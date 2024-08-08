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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.data.Resource
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.ui.CastAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.SeasonsAdapter
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL_ORIGINAL
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailTvBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toEpisodeString
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toVoteAverageFormat
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedDateString
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toFormattedProductionCompanies
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.toSeasonString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvActivity : AppCompatActivity() {

    private var _binding: ActivityDetailTvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailTvViewModel by viewModels()
    private lateinit var seasonsAdapter: SeasonsAdapter
    private lateinit var castAdapter: CastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        enableEdgeToEdge()
        handleWindowInsets()

        val tvId = intent.getIntExtra(EXTRA_TV_ID, 0)
        viewModel.fetchTvDetail(tvId)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        seasonsAdapter = SeasonsAdapter()
        castAdapter = CastAdapter { castId ->
            val intent = Intent(this, DetailPeopleActivity::class.java).apply {
                putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, castId)
            }
            startActivity(intent)
        }
        binding.detailRecyclerViewSeasons.adapter = seasonsAdapter
        binding.detailRecyclerViewCast.adapter = castAdapter
    }

    private fun setupObservers() {
        viewModel.isWatchlist.observe(this) { isWatchlist ->
            setFavoriteState(isWatchlist)
        }

        viewModel.tvDetail.observe(this) { detailTv ->
            handleTvDetail(detailTv)
        }

        viewModel.tvCast.observe(this) { castResource ->
            handleCastResource(castResource)
        }
    }

    private fun handleTvDetail(detailTv: Resource<DetailTv>) {
        when (detailTv) {
            is Resource.Error -> {
                binding.progressBar.visibility = View.GONE
                showToast(detailTv.message.toString())
            }

            is Resource.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Resource.Success -> {
                binding.progressBar.visibility = View.GONE
                detailTv.data?.let { setDetailTv(it) }
            }
        }
    }

    private fun handleCastResource(tvCastResource: Resource<List<TvCast>>) {
        when (tvCastResource) {
            is Resource.Error -> {
                binding.progressBarCast.visibility = View.GONE
                showToast(tvCastResource.message.toString())
            }

            is Resource.Loading -> {
                binding.progressBarCast.visibility = View.VISIBLE
            }

            is Resource.Success -> {
                binding.progressBarCast.visibility = View.GONE
                castAdapter.submitList(tvCastResource.data)
            }
        }
    }

    private fun setDetailTv(detailTv: DetailTv) {
        binding.apply {
            Glide.with(this@DetailTvActivity)
                .load(IMAGE_URL_ORIGINAL + detailTv.backdropPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(detailBackdropPath)

            Glide.with(this@DetailTvActivity)
                .load(IMAGE_URL_ORIGINAL + detailTv.posterPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                        .error(R.drawable.ic_error)
                )
                .into(detailPosterPath)

            detailTitle.text = detailTv.name
            detailOverview.text = detailTv.overview
            detailFirstAirDate.text = detailTv.firstAirDate.toFormattedDateString()
            detailNumberOfSeason.text = detailTv.numberOfSeasons.toSeasonString()
            detailNumberOfEpisode.text = detailTv.numberOfEpisodes.toEpisodeString()
            detailVoteAverage.text = detailTv.voteAverage.toVoteAverageFormat(1)
            detailGenres.text = detailTv.genres.joinToString(", ") { it.name }
            detailCompanies.text = detailTv.productionCompanies.map { it.name }.toFormattedProductionCompanies()

            seasonsAdapter.submitList(detailTv.seasons)
        }
    }

    private fun setupListeners() {
        binding.btnWatchlist.setOnClickListener {
            val currentDetail = viewModel.tvDetail.value?.data ?: return@setOnClickListener
            viewModel.setUserFavorite(
                WatchlistTv(
                    id = currentDetail.id,
                    name = currentDetail.name,
                    posterPath = currentDetail.posterPath,
                    firstAirDate = currentDetail.firstAirDate,
                    voteAverage = currentDetail.voteAverage
                )
            )
        }

        @Suppress("DEPRECATION")
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnWatchlist.setImageResource(R.drawable.baseline_watchlist_filled)
        } else {
            binding.btnWatchlist.setImageResource(R.drawable.baseline_watchlist)
        }
    }

    private fun setupBinding() {
        _binding = ActivityDetailTvBinding.inflate(layoutInflater)
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
        const val EXTRA_TV_ID = "extra_tv_id"
    }
}
