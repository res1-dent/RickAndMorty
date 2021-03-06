package com.sometime.rickandmorty.domain.interactors

import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.mappers.toEpisodeData
import com.sometime.rickandmorty.domain.usecases.GetPersonUseCase
import com.sometime.rickandmorty.domain.usecases.SetPersonUseCase
import timber.log.Timber
import javax.inject.Inject

class SetPersonUseCaseImpl @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
) : SetPersonUseCase {

    override suspend fun invoke(id: Int): Pair<Result<Person>, Result<List<Episode>>> {
        val result = getPersonUseCase(id)
        val episodes: Result<List<Episode>> = if (result.second.isSuccess) {
            Result.success(getEpisodesList(checkNotNull(result.second).getOrNull()))
        } else {
            Result.failure(result.second.exceptionOrNull() ?: Throwable())
        }
        return Pair(result.first, episodes)
    }


    private fun getEpisodesList(remoteEpisodeData: List<RemoteEpisodeData>?): List<Episode> {
        remoteEpisodeData ?: return emptyList()
        var currentEpisodeSeason = 0
        val episodeList = mutableListOf<Episode>()
        remoteEpisodeData.forEach {
            val seasonNumber = getSeasonNumber(it.episode)
            if (currentEpisodeSeason != seasonNumber) {
                currentEpisodeSeason = seasonNumber
                episodeList.add(Episode.Season(number = currentEpisodeSeason))
            }
            Timber.e("currentEpisodeSeason = $currentEpisodeSeason, seasonNumber = $seasonNumber")
            episodeList.add(it.toEpisodeData())
        }
        return episodeList
    }

    private fun getSeasonNumber(episode: String): Int {
        return episode[2].digitToInt()
    }
}