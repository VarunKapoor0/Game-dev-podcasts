package com.varun.gamedevpodcasts.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.dao.PodcastDao
import com.varun.gamedevpodcasts.data.database.entities.PodcastEntity
import com.varun.gamedevpodcasts.data.models.Podcast
import com.varun.gamedevpodcasts.data.repositories.EpisodeRoomRepository
import com.varun.gamedevpodcasts.data.repositories.PodcastRepository
import com.varun.gamedevpodcasts.data.repositories.RSSFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastListViewModel @Inject constructor(
    repository: EpisodeRoomRepository,
    nonDBrepository: RSSFeedRepository,
    podcastRepository: PodcastRepository,
    episodeDao: EpisodeDao,
    podcastDao: PodcastDao): ViewModel() {

    private val _podcastNames = MutableStateFlow<List<String>>(emptyList())
    val podcastNames: StateFlow<List<String>> = _podcastNames.asStateFlow()

    private val _podcastDetails = MutableStateFlow<List<PodcastEntity>>(emptyList())
    val podcastDetails: StateFlow<List<PodcastEntity>> = _podcastDetails.asStateFlow()

    init{
        viewModelScope.launch {
            podcastRepository.setPodcastDetails()
            podcastDao.getAllPodcasts().collect{ podcasts ->
                _podcastDetails.value = podcasts

            //var response = nonDBrepository.rssLinkData(nonDBrepository.pullingXmlParser())
            //_podcastDetails.value = response.podcastDetails
            //repository.insertFeedData()
            //episodeDao.getPodcastNames().collect { names ->
            //    _podcastNames.value = names
            }
        }

    }
}