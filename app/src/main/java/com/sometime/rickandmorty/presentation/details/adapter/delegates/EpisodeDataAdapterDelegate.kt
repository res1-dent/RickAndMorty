package com.sometime.rickandmorty.presentation.details.adapter.delegates

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.ItemListEpisodesBinding
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.utils.inflate

class EpisodeDataAdapterDelegate(
    private val onEpisodeClicked: (Episode.EpisodeData, view: View) -> Unit
) : AbsListItemAdapterDelegate<Episode.EpisodeData, Episode, EpisodeDataAdapterDelegate.Holder>() {

    override fun isForViewType(item: Episode, items: MutableList<Episode>, position: Int): Boolean {
        return item is Episode.EpisodeData
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(ItemListEpisodesBinding::inflate), onEpisodeClicked)
    }

    override fun onBindViewHolder(item: Episode.EpisodeData, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemListEpisodesBinding,
        private val onEpisodeClicked: (Episode.EpisodeData, view: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentEpisode: Episode.EpisodeData? = null

        init {
            binding.root.setOnClickListener {view->
                currentEpisode?.let { episode->
                    onEpisodeClicked(episode, view)
                }
            }
        }

        fun bind(item: Episode.EpisodeData) {
            binding.root.transitionName = binding.root.context.resources.getString(R.string.card_transition_name, item.id.toString())
            currentEpisode = item
            binding.episodeName.text = item.name
            binding.airDate.text = item.air_date
        }
    }
}

