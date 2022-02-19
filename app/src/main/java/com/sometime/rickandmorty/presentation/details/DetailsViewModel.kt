package com.sometime.rickandmorty.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sometime.rickandmorty.domain.entities.Person
import com.sometime.rickandmorty.domain.usecases.SetPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val setPersonUseCase: SetPersonUseCase
) : ViewModel() {


    private val _personStateFlow = MutableStateFlow<Person?>(null)
    val person = _personStateFlow.asStateFlow()

    fun getPersonInfo(id: Int) {
        viewModelScope.launch {
            val person = setPersonUseCase.invoke(id)
            if (person.isSuccess)
                _personStateFlow.value = person.getOrNull()
        }
    }

}