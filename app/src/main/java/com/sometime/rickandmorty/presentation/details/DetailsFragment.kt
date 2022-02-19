package com.sometime.rickandmorty.presentation.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.FragmentDetailsBinding
import com.sometime.rickandmorty.domain.entities.Person
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel by viewModels<DetailsViewModel>()
    private val binding: FragmentDetailsBinding by viewBinding(FragmentDetailsBinding::bind)
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = 400.toLong()
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPersonInfo(args.personId)
        observeViewModelState()
    }

    private fun observeViewModelState() {
        lifecycleScope.launchWhenCreated {
            viewModel.person.collect {
                it?.let { setData(it) }
            }
        }
    }

    private fun setData(it: Person) {
        binding.nameTextView.text = it.name
        Glide.with(requireView()).load(it.image).into(binding.avatarImageView)
    }
}