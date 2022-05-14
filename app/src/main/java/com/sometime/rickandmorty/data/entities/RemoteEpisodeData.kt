package com.sometime.rickandmorty.data.entities

import com.squareup.moshi.JsonClass
import retrofit2.http.Field

@JsonClass(generateAdapter = true)
data class RemoteEpisodeData(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
)

@JsonClass(generateAdapter = true)
data class RemoteEpisodesData(
    val info: Info,
    val results:List<RemoteEpisodeData>
)
