package com.fadhlansulistiyo.cinemadatabase.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)?.findNavController()

        if (navController != null) {
            navView.setupWithNavController(navController)
        } else {
            navView.visibility = View.GONE
        }
    }
}