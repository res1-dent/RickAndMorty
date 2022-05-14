package com.sometime.rickandmorty.presentation.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.sometime.rickandmorty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment: Fragment(R.layout.fragment_favourites) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()

    }
}