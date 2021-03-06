package com.sometime.rickandmorty.presentation.details

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.platform.MaterialArcMotion
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.FragmentDetailsBinding
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.presentation.adapter.PagingAdapter.Companion.getShimmer
import com.sometime.rickandmorty.presentation.details.adapter.EpisodeAdapter
import com.sometime.rickandmorty.presentation.details.adapter.animations.SlideInDownAnimator
import com.sometime.rickandmorty.utils.AppBarStateChangedListener
import com.sometime.rickandmorty.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel by viewModels<DetailsViewModel>()
    private val binding: FragmentDetailsBinding by viewBinding(FragmentDetailsBinding::bind)
    private var adapter: EpisodeAdapter by autoCleared()
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(false)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = 400.toLong()
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            binding.episodesRecycler.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        viewModel.getPersonInfo(args.personId)
        observeViewModelState()
        initList()
        binding.mainAppbar.addOnOffsetChangedListener(object :
            AppBarStateChangedListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                state ?: return
                val materialFade = MaterialFade().apply {
                    duration = 200L
                }
                TransitionManager.beginDelayedTransition(binding.mainAppbar, materialFade)
                when (state) {
                    State.COLLAPSED -> binding.avatarImageViewSmall.isVisible = true
                    else -> binding.avatarImageViewSmall.isVisible = false
                }
            }
        })
    }

    private fun initList() {
        adapter = EpisodeAdapter(::navigateToEpisodeFragment)
        with(binding.episodesRecycler) {
            adapter = this@DetailsFragment.adapter
            itemAnimator = SlideInDownAnimator()
        }
    }

    private fun navigateToEpisodeFragment(episodeData: Episode.EpisodeData, view: View) {
        val extras =
            FragmentNavigatorExtras(view to resources.getString(R.string.episode_detail_transition_name))
        exitTransition = MaterialElevationScale(false).apply {
            duration = 400.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 400.toLong()
        }
        val action = DetailsFragmentDirections.actionDetailsFragmentToEpisodeFragment(episodeData)
        findNavController().navigate(action,extras)
    }

    private fun observeViewModelState() {
        lifecycleScope.launchWhenCreated {
            viewModel.person.collect {
                it?.let { setData(it) }
            }
        }
        viewModel.episodes.observe(viewLifecycleOwner) {
            setEpisodesList(it)
        }
    }

    private fun setEpisodesList(remoteEpisode: List<Episode>) {
        adapter.items = remoteEpisode
    }

    private fun setData(it: Person) {
        binding.nameTextView.text = it.name
        val shimmer = getShimmer()
        Glide.with(requireView()).load(it.image).placeholder(shimmer).error(shimmer).addListener(
            object : RequestListener<Drawable>{
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    binding.chip.isVisible = true
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                   return false
                }

            })
            .into(binding.avatarImageView)
        Glide.with(requireView()).load(it.image).circleCrop().into(binding.avatarImageViewSmall)
        binding.chip.text = binding.root.resources.getString(
            R.string.species_name,
            it.status.replaceFirstChar { it.uppercase() },
            it.species
        )
        binding.chip.chipIconTint = when (it.status) {
            "Alive" -> ColorStateList.valueOf(Color.GREEN)
            "Dead" -> ColorStateList.valueOf(Color.RED)
            else -> ColorStateList.valueOf(Color.BLUE)
        }
    }
}