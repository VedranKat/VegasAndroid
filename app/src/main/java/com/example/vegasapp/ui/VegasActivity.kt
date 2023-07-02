package com.example.vegasapp.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.vegasapp.R
import com.example.vegasapp.databinding.ActivityVegasBinding
import com.example.vegasapp.ui.fragments.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VegasApplication: Application()

@AndroidEntryPoint
class VegasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVegasBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        fragmentManager = supportFragmentManager

        binding.bottomNavigationView?.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener() { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.registerFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

}