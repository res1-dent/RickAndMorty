package com.sometime.rickandmorty.presentation.details.adapter.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.SeasonNumberItemListBinding
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.utils.inflate

class SeasonAdapterDelegate(
) : AbsListItemAdapterDelegate<Episode.Season, Episode, SeasonAdapterDelegate.Holder>() {

    override fun isForViewType(item: Episode, items: MutableList<Episode>, position: Int): Boolean {
        return item is Episode.Season
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(SeasonNumberItemListBinding::inflate))
    }

    override fun onBindViewHolder(
        item: Episode.Season,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val binding: SeasonNumberItemListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Episode.Season) {
            binding.seasonName.text =
                binding.root.resources.getString(R.string.season_number, item.number)
        }
    }
}

