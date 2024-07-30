package com.fadhlansulistiyo.cinemadatabase.presenter.detail

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
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailPeople
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailPeopleBinding
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityDetailTvBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPeopleActivity : AppCompatActivity() {

    private var _binding: ActivityDetailPeopleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailPeopleViewModel by viewModels()

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

        viewModel.peopleDetail.observe(this) { detailPeople ->
            when (detailPeople) {
                is Resource.Error -> {
                    binding.progressBar.hide()
                    Log.e("DetailPeopleActivity", "peopleDetail, Error: ${detailPeople.message}")
                }

                is Resource.Loading -> {
                    binding.progressBar.show()
                    Log.d("DetailPeopleActivity", "peopleDetail, Loading")
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    Log.d("DetailPeopleActivity", "peopleDetail, Success: ${detailPeople.data}")
                    detailPeople.data?.let { setDetailPeople(it) }
                }
            }

        }
    }

    private fun setDetailPeople(detailPeople: DetailPeople) {
        binding.apply {
            Glide.with(this@DetailPeopleActivity)
                .load(IMAGE_URL + detailPeople.profilePath)
                .into(profileImage)
            name.text = detailPeople.name
            knownForDepartment.text = detailPeople.knownForDepartment
            birthday.text = detailPeople.birthday
            placeOfBirth.text = detailPeople.placeOfBirth
            gender.text = detailPeople.gender.toString()
            biography.text = detailPeople.biography
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