package com.varun.gamedevpodcasts.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import com.varun.gamedevpodcasts.data.repositories.EpisodeRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: EpisodeRoomRepository,
    episodeDao: EpisodeDao): ViewModel() {

    private val _individualEpisodeData = MutableStateFlow<EpisodeEntity?>(null)
    val individualEpisodeData: StateFlow<EpisodeEntity?> = _individualEpisodeData.asStateFlow()

    val guid: String? = savedStateHandle["guid"]

    init{
        viewModelScope.launch {
            episodeDao.getEpisodeDetails(guid).collect { episode ->
                _individualEpisodeData.value = episode
            }
        }
    }
}