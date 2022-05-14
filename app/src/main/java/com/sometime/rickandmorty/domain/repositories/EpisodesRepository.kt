package com.sometime.rickandmorty.domain.repositories

import com.sometime.rickandmorty.data.entities.RemoteEpisodeData

interface EpisodesRepository {
    suspend fun fetchEpisodes(): List<RemoteEpisodeData>
}