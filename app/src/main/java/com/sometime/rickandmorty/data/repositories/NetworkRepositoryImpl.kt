package com.sometime.rickandmorty.data.repositories


import androidx.paging.PagingSource
import com.sometime.rickandmorty.data.entities.RemoteEpisode
import com.sometime.rickandmorty.data.mappers.RemoteMapper
import com.sometime.rickandmorty.data.network.RickAndMortyApi
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.NetworkRepository
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val pagingSource: RickAndMortyPageSource.RickAndMortyPageSourceFactory,
    private val rickAndMortyApi: RickAndMortyApi,
    private val mapper: RemoteMapper
) : NetworkRepository {

    override fun invoke(query: String?): PagingSource<Int, Person> {
        return pagingSource.create(query?:"")
    }

    override suspend fun fetchPersonById(id: Int): Result<Person> {
        val personResponse = rickAndMortyApi.getPersonById(id)
        return if (personResponse.isSuccessful) {
            Timber.e("person = ${personResponse.body()}")
            fetchEpisodes(checkNotNull(personResponse.body()?.episode))
            val remotePerson = checkNotNull(personResponse.body()).let { mapper.toPerson(it) }
            Result.success(remotePerson)
        } else {
            Result.failure(HttpException(personResponse))
        }
    }

    override suspend fun fetchPersonInfoById(id: Int): Pair<Result<Person>, Result<List<RemoteEpisode>>> {
        val personResponse = rickAndMortyApi.getPersonById(id)
        return if (personResponse.isSuccessful) {
            Timber.e("person = ${personResponse.body()}")
            val episodes = fetchEpisodes(checkNotNull(personResponse.body()?.episode))
            val remotePerson = checkNotNull(personResponse.body()).let { mapper.toPerson(it) }
            Pair(Result.success(remotePerson), episodes)
        } else {
            Pair(
                Result.failure(HttpException(personResponse)),
                Result.failure((HttpException(personResponse)))
            )
        }
    }

    //TODO: optimize this crutch
    private suspend fun fetchEpisodes(episodesList: List<String>): Result<List<RemoteEpisode>> {
        if (episodesList.size > 1) {
            val episodesNumbers = episodesList.joinToString(",") { it.takeLastWhile { it != '/' } }
            val episodesResponse = rickAndMortyApi.getSeriesList(episodesNumbers)
            return if (episodesResponse.isSuccessful) {
                val result = checkNotNull(episodesResponse.body())
                Result.success(result)
            } else
                Result.failure(HttpException(episodesResponse))
        } else {
            val episodeNumber = episodesList.first().takeLastWhile { it != '/' }
            val episodeResponse = rickAndMortyApi.getSeries(episodeNumber)
            return if (episodeResponse.isSuccessful) {
                val result = checkNotNull(episodeResponse.body())
                Result.success(listOf(result))
            } else
                Result.failure(HttpException(episodeResponse))
        }
    }

}