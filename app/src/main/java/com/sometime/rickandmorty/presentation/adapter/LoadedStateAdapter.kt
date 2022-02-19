package com.sometime.rickandmorty.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sometime.rickandmorty.databinding.ItemLoadingBinding
import com.sometime.rickandmorty.utils.inflate

class LoadedStateAdapter : LoadStateAdapter<LoadedStateAdapter.Holder>() {


    class Holder(
        binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

    override fun getStateViewType(loadState: LoadState): Int = when (loadState) {
        is LoadState.NotLoading -> error("not supported")
        is LoadState.Error -> 1
        is LoadState.Loading -> 0
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        return when (loadState) {
            LoadState.Loading -> Holder(parent.inflate(ItemLoadingBinding::inflate))
            is LoadState.Error -> Holder(parent.inflate(ItemLoadingBinding::inflate))
            is LoadState.NotLoading -> error("not supported")
        }
    }


}