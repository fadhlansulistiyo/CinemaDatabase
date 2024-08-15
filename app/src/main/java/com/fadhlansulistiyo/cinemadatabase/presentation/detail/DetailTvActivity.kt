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
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTvWithCast
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.WatchlistTv
import com.fadhlansulistiyo.cinemadatabase.core.ui.CastAdapter
import com.fadhlansulistiyo.cinemadatabase.core.ui.SeasonsAdapter
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailTvBinding
import com.fadhlansulistiyo.cinemadatabase.presentation.utils.loadImageOriginal
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
        viewModel.fetchDetailTv(tvId)
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
        with(binding) {
            detailRecyclerViewSeasons.adapter = seasonsAdapter
            recyclerViewCastTv.adapter = castAdapter
        }
    }

    private fun setupObservers() {
        viewModel.isWatchlist.observe(this) { isWatchlist ->
            setWatchlistState(isWatchlist)
        }

        viewModel.detailTv.observe(this) { resource ->
            handleDetailTv(resource)
        }
    }

    private fun handleDetailTv(resource: Resource<DetailTvWithCast>) {
        when (resource) {
            is Resource.Loading -> showLoading(true)
            is Resource.Error -> {
                showLoading(false)
                showToast(resource.message.toString())
                binding.errorMsg.errorLayout.visibility = View.VISIBLE
                binding.errorMsg.textError.text = resource.message
            }

            is Resource.Success -> {
                showLoading(false)
                binding.layoutMainDetailTv.visibility = View.VISIBLE
                resource.data?.let {
                    setDetailTv(it.detail)
                    castAdapter.submitList(it.cast)
                }
            }

            else -> {}
        }
    }

    private fun setDetailTv(detailTv: DetailTv) {
        binding.apply {
            detailBackdropPath.loadImageOriginal(this@DetailTvActivity, detailTv.backdropPath)
            detailPosterPath.loadImageOriginal(this@DetailTvActivity, detailTv.posterPath)
            detailTitle.text = detailTv.name
            detailOverview.text = detailTv.overview
            detailFirstAirDate.text = detailTv.firstAirDate.toFormattedDateString()
            detailNumberOfSeason.text = detailTv.numberOfSeasons.toSeasonString()
            detailNumberOfEpisode.text = detailTv.numberOfEpisodes.toEpisodeString()
            detailVoteAverage.text = detailTv.voteAverage.toVoteAverageFormat(1)
            detailGenres.text = detailTv.genres.joinToString(", ") { it.name }
            detailCompanies.text =
                detailTv.productionCompanies.map { it.name }.toFormattedProductionCompanies()

            seasonsAdapter.submitList(detailTv.seasons)
        }
    }

    private fun setupListeners() {
        binding.btnWatchlist.setOnClickListener {
            val currentDetail = viewModel.detailTv.value?.data?.detail ?: return@setOnClickListener
            viewModel.toggleWatchlistTv(
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

    private fun setWatchlistState(isWatchlist: Boolean) {
        binding.btnWatchlist.setImageResource(
            if (isWatchlist) R.drawable.baseline_watchlist_filled else R.drawable.baseline_watchlist
        )
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
        const val EXTRA_TV_ID = "extra_tv_id"
    }
}
