package com.sometime.rickandmorty.domain.repositories

import androidx.paging.PagingSource
import com.sometime.rickandmorty.domain.entities.Person

interface NetworkRepository {

    operator fun invoke(): PagingSource<Int, Person>

    suspend fun fetchPersonById(id: Int): Result<Person>

}