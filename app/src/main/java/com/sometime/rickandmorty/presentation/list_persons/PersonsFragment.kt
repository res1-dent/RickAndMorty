package com.sometime.rickandmorty.presentation.list_persons

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.FragmentPersonsBinding
import com.sometime.rickandmorty.presentation.adapter.LoadedStateAdapter
import com.sometime.rickandmorty.presentation.adapter.PagingAdapter
import com.sometime.rickandmorty.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


@AndroidEntryPoint
class PersonsFragment : Fragment(R.layout.fragment_persons) {


    private val binding by viewBinding(FragmentPersonsBinding::bind)
    private val viewModel by viewModels<PersonsViewModel>()
    private var adapter: PagingAdapter by autoCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        initList()
        observeViewModelState()
    }

    private fun observeViewModelState() {
        lifecycleScope.launchWhenCreated {
            viewModel.listOfPersons.collectLatest { person ->
                adapter.submitData(person)
            }
        }
    }

    private fun initList() {
        adapter = PagingAdapter(::goToDetails).apply {
            addLoadStateListener { state ->
                //Timber.e("state = $state")
                val refreshState = state.refresh
                binding.personsRecyclerView.isVisible = refreshState != LoadState.Loading
                binding.progress.isVisible = refreshState == LoadState.Loading
                if (refreshState is LoadState.Error || state.append is LoadState.Error) {
                    Snackbar.make(
                        binding.root,
                        "Error loading persons",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        binding.personsRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadedStateAdapter(),
            footer = LoadedStateAdapter()
        )
    }

    private fun goToDetails(id: Int, view: View) {
        Timber.e("transition name = ${view.transitionName}")
        val extras =
            FragmentNavigatorExtras(view to resources.getString(R.string.card_detail_transition_name))
        exitTransition = MaterialElevationScale(false).apply {
            duration = 400.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 400.toLong()
        }
        val action = PersonsFragmentDirections.actionMainFragmentToDetailsFragment(id)
        findNavController().navigate(action, extras)
    }
}