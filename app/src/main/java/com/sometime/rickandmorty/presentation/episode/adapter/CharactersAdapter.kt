package com.sometime.rickandmorty.presentation.episode.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.ListItemPersonBinding
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.presentation.adapter.PagingAdapter
import com.sometime.rickandmorty.utils.inflate

class CharactersAdapter(
    private val onPersonClick: (personId: Int, View) -> Unit
) : ListAdapter<Person, CharactersAdapter.Holder>(PersonDiffUtilCallback) {


    private object PersonDiffUtilCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        private val binding: ListItemPersonBinding,
        private val onPersonClicked: (id: Int, view: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var personId: Int? = null

        init {
            binding.root.setOnClickListener {view->
                personId?.let {
                    onPersonClicked(it, view)
                }
            }

        }

        fun bind(item: Person) {
            personId = item.id
            val shimmerDrawable = PagingAdapter.getShimmer()
            binding.chip.text = binding.root.resources.getString(
                R.string.species_name,
                item.status.replaceFirstChar { it.uppercase() },
                item.species
            )
            binding.chip.chipIconTint = when (item.status) {
                "Alive" -> ColorStateList.valueOf(Color.GREEN)
                "Dead" -> ColorStateList.valueOf(Color.RED)
                else -> ColorStateList.valueOf(Color.BLUE)
            }
            binding.nameTextView.text = item.name
            Glide.with(itemView)
                .load(item.image)
                .placeholder(shimmerDrawable)
                .error(shimmerDrawable)
                .centerCrop()
                .into(binding.avatarImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            parent.inflate(ListItemPersonBinding::inflate), onPersonClick
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(getItem(position))
    }

}