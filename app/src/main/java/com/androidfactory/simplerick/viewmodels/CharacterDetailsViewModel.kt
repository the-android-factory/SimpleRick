package com.androidfactory.simplerick.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.simplerick.components.common.DataPoint
import com.androidfactory.simplerick.repositories.CharacterRepository
import com.androidfactory.simplerick.screens.CharacterDetailsViewState
import com.androidfactory.simplerick.ui.text.UiText
import com.androidfactory.simplerick.ui.text.stringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _internalStorageFlow = MutableStateFlow<CharacterDetailsViewState>(
        value = CharacterDetailsViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _internalStorageFlow.update { return@update CharacterDetailsViewState.Loading }
        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            val dataPoints = buildList {
                add(DataPoint("Last known location", UiText.DynamicText(character.location.name)))
                add(DataPoint("Species", UiText.DynamicText(character.species)))
                add(DataPoint("Gender", UiText.StringResource(character.gender.stringResource())))
                character.type.takeIf { it.isNotEmpty() }?.let { type ->
                    add(DataPoint("Type", UiText.DynamicText(type)))
                }
                add(DataPoint("Origin", UiText.DynamicText(character.origin.name)))
                add(DataPoint("Episode count", UiText.DynamicText(character.episodeIds.size.toString())))
            }
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Success(
                    character = character,
                    characterDataPoints = dataPoints
                )
            }
        }.onFailure { exception ->
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Error(
                    message = exception.message ?: "Unknown error occurred"
                )
            }
        }
    }
}