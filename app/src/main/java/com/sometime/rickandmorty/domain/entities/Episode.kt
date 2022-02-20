package com.sometime.rickandmorty.domain.entities

import kotlin.random.Random

sealed class Episode {
    data class Season(
        val id: Int = Random.nextInt(),
        val number: Int
    ) : Episode()

    data class EpisodeData(
        val id: Int,
        val name: String,
        val air_date: String,
    ): Episode()
}
