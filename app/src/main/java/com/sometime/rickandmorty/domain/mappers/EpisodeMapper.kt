package com.sometime.rickandmorty.domain.mappers

import com.sometime.rickandmorty.data.entities.RemoteEpisode
import com.sometime.rickandmorty.domain.entities.Episode
import javax.inject.Inject

class EpisodeMapper @Inject constructor() {

    fun toEpisodeData(remoteEpisode: RemoteEpisode): Episode.EpisodeData {
        return Episode.EpisodeData(
            id = remoteEpisode.id,
            air_date = remoteEpisode.air_date,
            name = remoteEpisode.name
        )
    }
}