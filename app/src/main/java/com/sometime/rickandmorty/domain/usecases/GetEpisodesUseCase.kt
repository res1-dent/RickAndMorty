package com.sometime.rickandmorty.domain.usecases

import com.sometime.rickandmorty.domain.entities.Episode

interface GetEpisodesUseCase {
    suspend fun getEpisodes(): Result<List<Episode>>
}