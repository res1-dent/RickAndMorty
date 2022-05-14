package com.sometime.rickandmorty.presentation.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.usecases.GetEpisodeCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val useCase: GetEpisodeCharactersUseCase
) : ViewModel(){

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons = _persons.asStateFlow()


    fun getPersons(characters: String){
        viewModelScope.launch {
            val result = useCase.fetchPersons(characters)
            _persons.value = result
            Timber.e("result = $result")
        }
    }
}