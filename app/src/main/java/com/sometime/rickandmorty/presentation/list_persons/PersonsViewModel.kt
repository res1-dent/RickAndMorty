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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val setPersonsUseCase: SetPersonsListUseCase,
) : ViewModel() {

    private var nameQuery: String? = null

    val listOfPersons: SharedFlow<PagingData<Person>> =
        Pager(PagingConfig(20, prefetchDistance = 3))
        {
            setPersonsUseCase(nameQuery)
        }.flow.cachedIn(
            viewModelScope
        ).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun bind(nameFlow: Flow<String>, callback: () -> Unit) {
          nameFlow
                .debounce(200)
                .distinctUntilChanged()
                .mapLatest {
                nameQuery = it
                callback()
            }.launchIn(viewModelScope)
    }
}