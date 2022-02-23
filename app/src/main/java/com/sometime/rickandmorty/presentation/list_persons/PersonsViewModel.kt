package com.sometime.rickandmorty.presentation.list_persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
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

    private val _nameQuery = MutableStateFlow("")
        private val nameQuery  = _nameQuery.asStateFlow()

    val persons: StateFlow<PagingData<Person>> = nameQuery
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    private fun newPager(query: String): Pager<Int, Person>{
        return Pager(PagingConfig(20, enablePlaceholders = true)){
            setPersonsUseCase(query)
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun bind(nameFlow: Flow<String>) {
          nameFlow
                .debounce(200)
                .distinctUntilChanged()
                .onEach {
                    _nameQuery.tryEmit(it)
            }.launchIn(viewModelScope)
    }
}