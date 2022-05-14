package com.sometime.rickandmorty.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), BottomBarController {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var doubleBackToExitPressedOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigation.itemIconTintList = null
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
    }

    override fun toggleBottomBar(flag: Boolean) {
        binding.bottomNavigation.isVisible = flag
    }


    override fun onBackPressed() {
        val currentDestId = findNavController(R.id.fragmentContainerView).currentDestination?.id
        if (currentDestId == R.id.mainFragment || currentDestId == R.id.episodesFragment || currentDestId == R.id.favouritesFragment) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                delay(2000)
                doubleBackToExitPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }

}