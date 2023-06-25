package com.example.vegasapp.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.vegasapp.R
import com.example.vegasapp.databinding.ActivityVegasBinding
import com.example.vegasapp.repository.NewsRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VegasApplication: Application()

@AndroidEntryPoint
class VegasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVegasBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        binding.bottomNavigationView?.setupWithNavController(navHostFragment.navController)
    }

}