package com.sometime.rickandmorty.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sometime.rickandmorty.data.entities.RemoteEpisode
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.usecases.SetPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val setPersonUseCase: SetPersonUseCase
) : ViewModel() {


    private val _personStateFlow = MutableStateFlow<Person?>(null)
    val person = _personStateFlow.asStateFlow()

    private val _episodesStateFlow = MutableLiveData<List<Episode>>(emptyList())
    val episodes: LiveData<List<Episode>> = _episodesStateFlow


    fun getPersonInfo(id: Int) {
        viewModelScope.launch {
            val person = setPersonUseCase.invoke(id)
            if (person.first.isSuccess)
                _personStateFlow.value = person.first.getOrNull()
            if (person.second.isSuccess) {
                val episodes =  person.second.getOrDefault(emptyList())
                Timber.e("episode in viewModels = $episodes")
                _episodesStateFlow.value = episodes
            }
        }
    }

}