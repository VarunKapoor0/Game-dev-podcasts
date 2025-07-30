package com.varun.gamedevpodcasts.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import com.varun.gamedevpodcasts.data.repositories.EpisodeRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewmodel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: EpisodeRoomRepository,
    private val episodeDao: EpisodeDao
): ViewModel() {

    val podcastName: String? = savedStateHandle["podcast_name"]

    private val _episodes = MutableStateFlow<List<EpisodeEntity>>(emptyList())
    val episodes: StateFlow<List<EpisodeEntity>> = _episodes.asStateFlow()

init {
    viewModelScope.launch {
        //Log.d("EpisodeListViewModel", "The podcast name is : $podcastName")
        episodeDao.delete()
        repository.insertFeedData()
        episodeDao.deleteDuplicateEpisodes()
        Log.d("ViewModel", "The podcast name for episode extraction is : $podcastName")
        episodeDao.getPodcastEpisodes(podcastName).collect { episodeList ->
            //Log.d("ViewModel", "The data from the db is : $episodeList")
            _episodes.value = episodeList
        }
        Log.d("ViewModel", "The data has been added to the room db. ")
    }
}

    fun onSeasonSelected(season: Int?){
        viewModelScope.launch {
            episodeDao.getSeasonEpisodes(podcastName.toString(), season).collect { seasonalEpisodeList ->
                _episodes.value = seasonalEpisodeList
            }
        }
    }

    /*TODO: Send individual card data to the UI. Each link has multiple podcast episodes under it,
    *  therefore it has to be a list. Already getting responseList, but it contains all the details of all episodes from
    *  all links. Need more nesting in the model. MODEL HAS TO BE CHANGED.
    */
}