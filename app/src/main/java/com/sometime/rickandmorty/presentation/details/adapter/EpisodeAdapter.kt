package com.sometime.rickandmorty.presentation.details.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.presentation.details.adapter.delegates.EpisodeDataAdapterDelegate
import com.sometime.rickandmorty.presentation.details.adapter.delegates.SeasonAdapterDelegate


class EpisodeAdapter(
    onEpisodeClicked: (Episode.EpisodeData, view: View) -> Unit
) : AsyncListDifferDelegationAdapter<Episode>(EpisodeDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(SeasonAdapterDelegate())
        delegatesManager.addDelegate(EpisodeDataAdapterDelegate(onEpisodeClicked))
    }

    class EpisodeDiffUtilCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return when {
                oldItem is Episode.Season && newItem is Episode.Season -> oldItem.id == newItem.id
                oldItem is Episode.EpisodeData && newItem is Episode.EpisodeData -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return when {
                oldItem is Episode.Season && newItem is Episode.Season -> oldItem == newItem
                oldItem is Episode.EpisodeData && newItem is Episode.EpisodeData -> oldItem == newItem
                else -> false
            }
        }
    }
}
