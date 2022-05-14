package com.sometime.rickandmorty.domain.repositories

import androidx.paging.PagingSource
import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.domain.entities.Person

interface NetworkRepository {

    operator fun invoke(query: String?): PagingSource<Int, Person>

    suspend fun fetchPersonById(id: Int): Result<Person>

    suspend fun fetchPersonInfoById(id: Int): Pair<Result<Person>, Result<List<RemoteEpisodeData>>>
    suspend fun fetchEpisodes(episodesList: List<String>): Result<List<RemoteEpisodeData>>
}