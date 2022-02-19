package com.sometime.rickandmorty.di

import com.sometime.rickandmorty.domain.interactors.GetPersonUseCaseImpl
import com.sometime.rickandmorty.domain.interactors.GetPersonsListUseCaseImpl
import com.sometime.rickandmorty.domain.interactors.SetPersonUseCaseImpl
import com.sometime.rickandmorty.domain.interactors.SetPersonsListUseCaseImpl
import com.sometime.rickandmorty.domain.usecases.GetPersonUseCase
import com.sometime.rickandmorty.domain.usecases.GetPersonsListUseCase
import com.sometime.rickandmorty.domain.usecases.SetPersonUseCase
import com.sometime.rickandmorty.domain.usecases.SetPersonsListUseCase
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
}