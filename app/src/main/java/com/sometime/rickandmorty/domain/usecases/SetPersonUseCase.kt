package com.sometime.rickandmorty.domain.usecases

import com.sometime.rickandmorty.domain.entities.Person

interface SetPersonUseCase {
    suspend operator fun invoke(id: Int): Result<Person>
}