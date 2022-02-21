package com.sometime.rickandmorty.domain.usecases

import androidx.paging.PagingSource
import com.sometime.rickandmorty.domain.entities.Person

interface GetPersonsListUseCase {
    operator fun invoke(query: String?): PagingSource<Int, Person>
}