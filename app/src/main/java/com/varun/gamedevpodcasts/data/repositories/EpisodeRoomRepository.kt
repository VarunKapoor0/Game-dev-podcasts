package com.varun.gamedevpodcasts.data.repositories

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import com.varun.gamedevpodcasts.data.models.Episode
import com.varun.gamedevpodcasts.data.models.Podcast
import com.varun.gamedevpodcasts.data.models.RssResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeRoomRepository @Inject constructor(
    private val episodeDao: EpisodeDao,
    private val rssFeedRepository: RSSFeedRepository) {

    private val _episodes = MutableStateFlow<List<EpisodeEntity>>(emptyList())
    val episodes: StateFlow<List<EpisodeEntity>> = _episodes.asStateFlow()

    init{

    }

    suspend fun insertFeedData(){
        Log.d("RoomRepo", "Starting to add episode values inside room db. ")
        var episodeEntityList: List<EpisodeEntity> = listOf<EpisodeEntity>()
        var episodeEntityArrayList: ArrayList<EpisodeEntity> = arrayListOf<EpisodeEntity>()

        var list: ArrayList<Podcast> = arrayListOf<Podcast>()
        var rssLinks: ArrayList<String> = arrayListOf<String>()
        rssLinks = rssFeedRepository.pullingXmlParser()
        var responseList: RssResponse = rssFeedRepository.rssLinkData(rssLinks)
        list = responseList.podcastDetails
        var id = 0
        for(value in list){
            for(item in value.details){
                id++
                var entity = EpisodeEntity(id, item.title, item.description, item.episodeNumber)
                episodeEntityArrayList.add(entity)
            }
        }
        episodeEntityList = episodeEntityArrayList
        episodeDao.insertEpisode(episodeEntityList)
        Log.d("RoomRepo", "Added the data to the room repo. ")
    }

    suspend fun getAllData(): List<EpisodeEntity>{

        episodeDao.getAllEpisodes().collect { episodeList ->
            _episodes.value = episodeList
        }

        return episodes.value
    }


}