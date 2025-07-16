package com.varun.gamedevpodcasts.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import com.varun.gamedevpodcasts.data.repositories.RSSFeedRepository
import com.varun.gamedevpodcasts.data.models.Podcast
import com.varun.gamedevpodcasts.data.models.RssResponse
import com.varun.gamedevpodcasts.data.repositories.EpisodeRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastListViewmodel @Inject constructor(
    private val repository: EpisodeRoomRepository,
    private val episodeDao: EpisodeDao
): ViewModel() {

    private val _title = MutableStateFlow<List<String>>(emptyList())
    val title: StateFlow<List<String>> = _title

    private val _description = MutableStateFlow<List<String>>(emptyList())
    val description: StateFlow<List<String>> = _description

    private val _episodes = MutableStateFlow<List<EpisodeEntity>>(emptyList())
    val episodes: StateFlow<List<EpisodeEntity>> = _episodes.asStateFlow()

init {
    viewModelScope.launch {
        episodeDao.getAllEpisodes().collect { episodeList ->
            Log.d("ViewModel", "The data from the db is : $episodeList")
            _episodes.value = episodeList
        }
        /*
        var rssLinks: ArrayList<String> = arrayListOf<String>()
        rssLinks = repository.pullingXmlParser()
        var responseList: RssResponse = repository.rssLinkData(rssLinks)
        list = responseList.podcastDetails
        */
        //list = repository.insertFeedData()
        Log.d("ViewModel", "The data has been added to the room db. ")
        //entityList = repository.getAllData()
        //Log.d("ViewModel", "The data from the db is : $entityList")
    }
}



    /*fun podcastList(){
        var list: ArrayList<EpisodeEntity> = arrayListOf<EpisodeEntity>()
        var entityList: List<EpisodeEntity> = listOf<EpisodeEntity>()
        viewModelScope.launch {
            episodeDao.getAllEpisodes().collect { episodeList ->
                Log.d("ViewModel", "The data from the db is : $episodeList")
                _episodes.value = episodeList
            }
            *//*
            var rssLinks: ArrayList<String> = arrayListOf<String>()
            rssLinks = repository.pullingXmlParser()
            var responseList: RssResponse = repository.rssLinkData(rssLinks)
            list = responseList.podcastDetails
            *//*
            //list = repository.insertFeedData()
            Log.d("ViewModel", "The data has been added to the room db. ")
            //entityList = repository.getAllData()
            //Log.d("ViewModel", "The data from the db is : $entityList")
        }

        //return entityList
    }*/

    /*TODO: Send individual card data to the UI. Each link has multiple podcast episodes under it,
    *  therefore it has to be a list. Already getting responseList, but it contains all the details of all episodes from
    *  all links. Need more nesting in the model. MODEL HAS TO BE CHANGED.
    */
}