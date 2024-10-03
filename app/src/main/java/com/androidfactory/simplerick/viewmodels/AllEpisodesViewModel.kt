package com.androidfactory.simplerick.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.simplerick.repositories.EpisodesRepository
import com.androidfactory.simplerick.screens.AllEpisodesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllEpisodesViewModel @Inject constructor(
    private val repository: EpisodesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AllEpisodesUiState>(AllEpisodesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun refreshAllEpisodes(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (forceRefresh) _uiState.update { AllEpisodesUiState.Loading }
        repository.fetchAllEpisodes()
            .onSuccess { episodeList ->
                _uiState.update {
                    AllEpisodesUiState.Success(
                        data = episodeList.groupBy {
                            it.seasonNumber.toString()
                        }.mapKeys {
                            "Season ${it.key}"
                        }
                    )
                }
            }.onFailure {
                _uiState.update { AllEpisodesUiState.Error }
            }
    }
}