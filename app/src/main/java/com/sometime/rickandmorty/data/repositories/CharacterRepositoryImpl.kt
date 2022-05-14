package com.sometime.rickandmorty.data.repositories

import com.sometime.rickandmorty.data.mappers.toPerson
import com.sometime.rickandmorty.data.network.RickAndMortyApi
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.CharacterRepository
import timber.log.Timber
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
        private val api: RickAndMortyApi
    ): CharacterRepository {

    override suspend fun fetchCharacters(characters: String): List<Person> {
        return api.getEpisodeCharacters(characters).map {
            it.toPerson()
        }
    }
}