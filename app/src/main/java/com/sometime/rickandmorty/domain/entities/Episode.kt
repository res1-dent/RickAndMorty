package com.sometime.rickandmorty.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

sealed class Episode {
    data class Season(
        val id: Int = Random.nextInt(),
        val number: Int
    ) : Episode()

    @Parcelize
    data class EpisodeData(
        val id: Int,
        val name: String,
        val air_date: String,
        val characters: List<Int>
    ) : Episode(), Parcelable
}
