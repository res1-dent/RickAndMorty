package com.sometime.rickandmorty.domain.interactors

import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.usecases.GetPersonUseCase
import com.sometime.rickandmorty.domain.usecases.SetPersonUseCase
import javax.inject.Inject

class SetPersonUseCaseImpl @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase
) : SetPersonUseCase {

    override suspend fun invoke(id: Int): Result<Person> {
        return getPersonUseCase(id)
    }
}