package com.sometime.rickandmorty.domain.mappers

import android.net.Uri
import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.domain.entities.Episode
import timber.log.Timber


fun RemoteEpisodeData.toEpisodeData(): Episode.EpisodeData {
    return Episode.EpisodeData(
        id = this.id,
        air_date = this.air_date,
        name = this.name,
        characters = this.characters.map {charactersUrl-> Uri.parse(charactersUrl).lastPathSegment?.toInt() ?: 0 }
    )
}