package com.sometime.rickandmorty.di

import com.sometime.rickandmorty.data.repositories.CharacterRepositoryImpl
import com.sometime.rickandmorty.data.repositories.EpisodesRepositoryImpl
import com.sometime.rickandmorty.data.repositories.NetworkRepositoryImpl
import com.sometime.rickandmorty.domain.repositories.CharacterRepository
import com.sometime.rickandmorty.domain.repositories.EpisodesRepository
import com.sometime.rickandmorty.domain.repositories.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    abstract fun providesCharacterRepository(impl:CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun providesEpisodesRepository(impl:EpisodesRepositoryImpl): EpisodesRepository

}