package com.sometime.rickandmorty.data.repositories

import androidx.paging.PagingSource
import com.sometime.rickandmorty.data.mappers.RemoteMapper
import com.sometime.rickandmorty.data.network.RickAndMortyApi
import com.sometime.rickandmorty.data.network.RickAndMortyPageSource
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.NetworkRepository
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val pagingSource: RickAndMortyPageSource,
    private val rickAndMortyApi: RickAndMortyApi,
    private val mapper: RemoteMapper
) : NetworkRepository {
    override fun invoke(): PagingSource<Int, Person> {
        return pagingSource
    }

    override suspend fun fetchPersonById(id: Int): Result<Person> {
        val personResponse = rickAndMortyApi.getPersonById(id)
        return if (personResponse.isSuccessful) {
            Timber.e("person = ${personResponse.body()}")
            val remotePerson = checkNotNull(personResponse.body()).let { mapper.toPerson(it) }
            Result.success(remotePerson)
        } else {
            Result.failure(HttpException(personResponse))
        }
    }

}