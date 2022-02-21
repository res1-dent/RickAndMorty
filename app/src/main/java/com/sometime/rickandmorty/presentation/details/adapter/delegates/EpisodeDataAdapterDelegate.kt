package com.sometime.rickandmorty.presentation.details.adapter.delegates

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.sometime.rickandmorty.databinding.ItemListEpisodesBinding
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.utils.inflate

class EpisodeDataAdapterDelegate(
    private val onEpisodeClicked: (id: Int, view: View) -> Unit
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
        private val onEpisodeClicked: (id: Int, view: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }

        fun bind(item: Episode.EpisodeData) {
            binding.episodeName.text = item.name
            binding.airDate.text = item.air_date
        }
    }
}

