package com.sometime.rickandmorty.domain.interactors

import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.CharacterRepository
import com.sometime.rickandmorty.domain.usecases.GetEpisodeCharactersUseCase
import javax.inject.Inject

class GetEpisodeCharactersUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : GetEpisodeCharactersUseCase {

    override suspend fun  fetchPersons(characters: String): List<Person> {
        return repository.fetchCharacters(characters)
    }
}