package com.sometime.rickandmorty.presentation.list_persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.usecases.SetPersonsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val setPersonsUseCase: SetPersonsListUseCase,
) : ViewModel() {


    val listOfPersons: StateFlow<PagingData<Person>> =
        Pager(PagingConfig(20, prefetchDistance = 3)) { setPersonsUseCase() }.flow.cachedIn(
            viewModelScope
        ).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
}