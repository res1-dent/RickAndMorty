package com.sometime.rickandmorty.domain.interactors

import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.NetworkRepository
import com.sometime.rickandmorty.domain.usecases.GetPersonUseCase
import javax.inject.Inject

class GetPersonUseCaseImpl @Inject constructor(
    private val repository: NetworkRepository
) : GetPersonUseCase {

    override suspend fun invoke(id: Int): Pair<Result<Person>, Result<List<RemoteEpisodeData>>> {
        return repository.fetchPersonInfoById(id = id)
    }

}