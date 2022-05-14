package com.sometime.rickandmorty.di

import com.sometime.rickandmorty.domain.interactors.*
import com.sometime.rickandmorty.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun providesGetPersonsListUseCase(impl: GetPersonsListUseCaseImpl): GetPersonsListUseCase

    @Binds
    abstract fun providesSetPersonsListUseCase(impl: SetPersonsListUseCaseImpl): SetPersonsListUseCase

    @Binds
    abstract fun providesGetPersonUseCase(impl: GetPersonUseCaseImpl): GetPersonUseCase

    @Binds
    abstract fun providesSetPersonUseCase(impl: SetPersonUseCaseImpl): SetPersonUseCase

    @Binds
    abstract fun providesGetEpisodeCharactersUseCase(impl: GetEpisodeCharactersUseCaseImpl): GetEpisodeCharactersUseCase

    @Binds
    abstract fun providesGetEpisodesUseCase(impl: GetEpisodesUseCaseImpl): GetEpisodesUseCase
}