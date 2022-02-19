package com.sometime.rickandmorty.domain.interactors

import androidx.paging.PagingSource
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.NetworkRepository
import com.sometime.rickandmorty.domain.usecases.GetPersonsListUseCase
import javax.inject.Inject

class GetPersonsListUseCaseImpl @Inject constructor(
    private val repository: NetworkRepository
) : GetPersonsListUseCase {
    override fun invoke(): PagingSource<Int, Person> {
        return repository()
    }
}