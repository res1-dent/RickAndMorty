package com.sometime.rickandmorty.data.repositories

import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.data.network.RickAndMortyApi
import com.sometime.rickandmorty.domain.repositories.EpisodesRepository
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) : EpisodesRepository {

    override suspend fun fetchEpisodes(): List<RemoteEpisodeData> {
        var page = 1
        val episodes = rickAndMortyApi.getAllSeries(page)
        val result = checkNotNull(episodes.results).toMutableList()
        val maxPages: Int = episodes.info.pages
        page++
        while (page <= maxPages) {
            rickAndMortyApi.getAllSeries(page).results.forEach { result.add(it) }
            page++
        }
        return result
    }
}
