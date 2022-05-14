package com.sometime.rickandmorty.presentation.episode

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.FragmentEpisodeBinding
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.presentation.adapter.LoadedStateAdapter
import com.sometime.rickandmorty.presentation.adapter.PagingAdapter
import com.sometime.rickandmorty.presentation.episode.adapter.CharactersAdapter
import com.sometime.rickandmorty.utils.autoCleared
import com.sometime.rickandmorty.utils.textChangedFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import timber.log.Timber

@AndroidEntryPoint
class EpisodeFragment : Fragment(R.layout.fragment_episode) {

    private val binding by viewBinding(FragmentEpisodeBinding::bind)
    private val args by navArgs<EpisodeFragmentArgs>()
    private val viewModel by viewModels<EpisodeViewModel>()
    private var adapter: CharactersAdapter by autoCleared()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = 400.toLong()
            scrimColor = Color.TRANSPARENT
            interpolator = AccelerateInterpolator()
        }
        exitTransition = MaterialFade()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            binding.personsList.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        setEpisodeInfo(args.episode)
        viewModel.getPersons(args.episode.characters.joinToString(","))
        initList()
        observeViewModelState()
    }


    private fun initList() {
        adapter = CharactersAdapter(::navigateToPersonDetailed)
        with(binding.personsList){
            adapter = this@EpisodeFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToPersonDetailed(personId: Int, view: View) {
        val action = EpisodeFragmentDirections.actionEpisodeFragmentToDetailsFragment(personId)
        findNavController().navigate(action)
    }

    private fun observeViewModelState() {
        lifecycleScope.launchWhenCreated {
            viewModel.persons.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    private fun setEpisodeInfo(episode: Episode.EpisodeData){
        binding.run {
            episodeName.text = episode.name
            airDate.text = episode.air_date
        }
    }
}