package com.varun.gamedevpodcasts.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.repositories.EpisodeRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastListViewModel @Inject constructor(
    repository: EpisodeRoomRepository,
    episodeDao: EpisodeDao): ViewModel() {

    private val _podcastNames = MutableStateFlow<List<String>>(emptyList())
    val podcastNames: StateFlow<List<String>> = _podcastNames.asStateFlow()

    init{
        viewModelScope.launch {
            repository.insertFeedData()
            episodeDao.getPodcastNames().collect { names ->
                _podcastNames.value = names
            }
        }

    }
}