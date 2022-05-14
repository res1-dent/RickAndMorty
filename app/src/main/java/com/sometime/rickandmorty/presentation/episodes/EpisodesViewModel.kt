package com.sometime.rickandmorty.presentation.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sometime.rickandmorty.domain.entities.Episode
import com.sometime.rickandmorty.domain.usecases.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesUseCase: GetEpisodesUseCase
) : ViewModel() {


    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes = _episodes.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    init {
        viewModelScope.launch {
            val fetchedEpisodes =  episodesUseCase.getEpisodes()
            when {
                fetchedEpisodes.isSuccess -> _episodes.value = fetchedEpisodes.getOrDefault(
                    emptyList())
                fetchedEpisodes.isFailure -> _error.value = fetchedEpisodes.exceptionOrNull()?.message
            }
        }
    }

}