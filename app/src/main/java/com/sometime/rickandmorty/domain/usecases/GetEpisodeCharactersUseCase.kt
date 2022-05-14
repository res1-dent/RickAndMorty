package com.sometime.rickandmorty.domain.usecases

import com.sometime.rickandmorty.domain.entities.Person

interface GetEpisodeCharactersUseCase {

    suspend fun fetchPersons(characters: String): List<Person>

}