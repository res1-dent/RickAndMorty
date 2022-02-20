package com.sometime.rickandmorty.domain.usecases

import com.sometime.rickandmorty.data.entities.RemoteEpisode
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.domain.entities.Person

interface SetPersonUseCase {
    suspend operator fun invoke(id: Int): Pair<Result<Person>, Result<List<Episode>>>
}