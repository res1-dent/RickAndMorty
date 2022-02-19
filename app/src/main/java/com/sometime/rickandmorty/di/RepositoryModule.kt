package com.sometime.rickandmorty.di

import androidx.paging.PagingSource
import com.sometime.rickandmorty.data.network.RickAndMortyPageSource
import com.sometime.rickandmorty.data.repositories.NetworkRepositoryImpl
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.repositories.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesPagingSource(impl: RickAndMortyPageSource): PagingSource<Int, Person>

    @Binds
    abstract fun providesNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository
}