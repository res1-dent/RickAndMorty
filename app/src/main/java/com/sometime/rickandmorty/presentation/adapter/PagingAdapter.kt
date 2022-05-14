package com.sometime.rickandmorty.presentation.adapter

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.sometime.rickandmorty.R
import com.sometime.rickandmorty.databinding.ListItemPersonBinding
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.utils.inflate

class PagingAdapter(
    private val onPersonClicked: (id: Int, view: View) -> Unit
) : PagingDataAdapter<Person, PagingAdapter.Holder>(PersonDiffUtilCallback) {


    class Holder(
        private val binding: ListItemPersonBinding,
        private val onPersonClicked: (id: Int, view: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Person?) {
            item ?: return
            binding.cardView.transitionName = binding.root.context.resources.getString(
                R.string.card_transition_name,
                item.id.toString()
            )

            val shimmerDrawable = getShimmer()
            binding.root.setOnClickListener {
                onPersonClicked(item.id, binding.cardView)
            }
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

    private object PersonDiffUtilCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            parent.inflate(ListItemPersonBinding::inflate),
            onPersonClicked
        )
    }

     companion object {
        fun getShimmer(): ShimmerDrawable {
            val shimmer =
                Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                    .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                    .setBaseAlpha(0.7f) //the alpha of the underlying children
                    .setHighlightAlpha(0.6f) // the shimmer alpha amount
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .setRepeatMode(ValueAnimator.INFINITE)
                    .setRepeatCount(100)
                    .build()

            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
            return shimmerDrawable
        }
    }
}