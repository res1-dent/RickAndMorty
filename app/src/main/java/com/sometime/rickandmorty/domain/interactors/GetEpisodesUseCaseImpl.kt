package com.sometime.rickandmorty.domain.interactors

import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.domain.mappers.toEpisodeData
import com.sometime.rickandmorty.domain.repositories.EpisodesRepository
import com.sometime.rickandmorty.domain.usecases.GetEpisodesUseCase
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class GetEpisodesUseCaseImpl @Inject constructor(
    private val repository: EpisodesRepository
): GetEpisodesUseCase {

    override suspend fun getEpisodes(): Result<List<Episode>> {
      val result = repository.fetchEpisodes()
        return if (result.isNotEmpty()) {
            Result.success(getEpisodesList(result))
        } else {
            Result.failure(Exception("Error"))
        }
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