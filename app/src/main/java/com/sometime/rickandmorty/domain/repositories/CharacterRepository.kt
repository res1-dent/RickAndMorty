package com.sometime.rickandmorty.domain.repositories

import com.sometime.rickandmorty.domain.entities.Person

interface CharacterRepository {

   suspend fun fetchCharacters(characters: String): List<Person>
}