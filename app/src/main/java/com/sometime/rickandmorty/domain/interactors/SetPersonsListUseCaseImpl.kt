package com.sometime.rickandmorty.domain.interactors

import androidx.paging.PagingSource
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.usecases.GetPersonsListUseCase
import com.sometime.rickandmorty.domain.usecases.SetPersonsListUseCase
import javax.inject.Inject

class SetPersonsListUseCaseImpl @Inject constructor(
    private val getPersonsListUseCase: GetPersonsListUseCase,
) : SetPersonsListUseCase {
    override operator fun invoke(): PagingSource<Int, Person> {
        return getPersonsListUseCase.invoke()
    }
}