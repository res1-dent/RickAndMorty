package com.sometime.rickandmorty.data.network

import androidx.annotation.IntRange
import com.sometime.rickandmorty.data.entities.RemoteEpisodeData
import com.sometime.rickandmorty.data.entities.RemoteEpisodesData
import com.sometime.rickandmorty.data.entities.RemotePerson
import com.sometime.rickandmorty.data.entities.RemotePersonsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("name") name: String? = null,
        @Query("page") @IntRange(from = 1) page: Int? = 1,
    ): Response<RemotePersonsList>

    @GET("character/{id}")
    suspend fun getPersonById(
        @Path("id") id: Int
    ): Response<RemotePerson>


    @GET("episode/{numbers}")
    suspend fun getSeriesList(
        @Path("numbers") episodes: String
    ): Response<List<RemoteEpisodeData>>

    @GET("episode/{number}")
    suspend fun getSeries(
        @Path("number") episodes: String
    ): Response<RemoteEpisodeData>

    @GET("episode")
    suspend fun getAllSeries(
        @Query("page") page: Int?
    ): RemoteEpisodesData

    @GET("character/{characters}")
    suspend fun getEpisodeCharacters(
        @Path("characters") characters: String
    ): List<RemotePerson>


}