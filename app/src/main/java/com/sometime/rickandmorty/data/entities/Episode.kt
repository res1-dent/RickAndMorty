package com.sometime.rickandmorty.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    val name: String,
    val air_date: String,
    val episode: String
)
