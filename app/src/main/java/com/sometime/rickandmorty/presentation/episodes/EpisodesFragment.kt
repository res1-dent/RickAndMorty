package com.sometime.rickandmorty.presentation.episodes

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.FragmentEpisodesBinding
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.presentation.details.DetailsFragmentDirections
import com.sometime.rickandmorty.presentation.details.adapter.EpisodeAdapter
import com.sometime.rickandmorty.presentation.details.adapter.animations.SlideInDownAnimator
import com.sometime.rickandmorty.presentation.main.BottomBarController
import com.sometime.rickandmorty.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EpisodesFragment : Fragment(R.layout.fragment_episodes) {

    private val binding by viewBinding(FragmentEpisodesBinding::bind)
    private var adapter: EpisodeAdapter by autoCleared()
    private val viewModel: EpisodesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BottomBarController).toggleBottomBar(true)
        postponeEnterTransition()
        view.doOnPreDraw {
            binding.episodesRecycler.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        initList()
        observeViewModelState()
    }

    private fun observeViewModelState() {
        viewModel.episodes.onEach {
            adapter.items = it
        }.launchIn(lifecycleScope)
    }

    private fun navigateToEpisodeFragment(episodeData: Episode.EpisodeData, view: View) {
        (requireActivity() as BottomBarController).toggleBottomBar(false)
        val extras =
            FragmentNavigatorExtras(view to resources.getString(R.string.episode_detail_transition_name))
        exitTransition = MaterialElevationScale(false).apply {
            duration = 400.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 400.toLong()
        }
        val action = EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeFragment(episodeData)
        findNavController().navigate(action,extras)
    }

    private fun initList() {
        adapter = EpisodeAdapter(::navigateToEpisodeFragment)
        with(binding.episodesRecycler) {
            adapter = this@EpisodesFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = SlideInDownAnimator()
        }
    }
}