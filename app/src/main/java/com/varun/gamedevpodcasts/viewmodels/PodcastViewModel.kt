package com.varun.gamedevpodcasts.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.dao.PodcastDao
import com.varun.gamedevpodcasts.data.database.entities.PodcastEntity
import com.varun.gamedevpodcasts.data.repositories.EpisodeRoomRepository
import com.varun.gamedevpodcasts.data.repositories.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    podcastDao: PodcastDao,
    episodeDao: EpisodeDao,
    episodeRoomRepository: EpisodeRoomRepository,
    podcastRepository: PodcastRepository
): ViewModel() {

    val pName: String? = savedStateHandle["p_name"]

    val _podcastDetails = MutableStateFlow<PodcastEntity?>(null)
    val podcastDetails: StateFlow<PodcastEntity?> = _podcastDetails.asStateFlow()

    init{
        viewModelScope.launch {
            podcastDao.getPodcast(pName).collect { details ->
                _podcastDetails.value = details
            }
        }
    }
}