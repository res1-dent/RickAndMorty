package com.sometime.rickandmorty.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemotePersonsList(
    val info: Info,
    val results: List<RemotePerson>
)