package com.varun.gamedevpodcasts.data.repositories

import android.util.Log
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import com.varun.gamedevpodcasts.data.models.Podcast
import com.varun.gamedevpodcasts.data.models.RssResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class EpisodeRoomRepository @Inject constructor(
    private val episodeDao: EpisodeDao,
    private val rssFeedRepository: RSSFeedRepository) {

    private val _episodes = MutableStateFlow<List<EpisodeEntity>>(emptyList())
    val episodes: StateFlow<List<EpisodeEntity>> = _episodes.asStateFlow()

    private val _individualEpisodeData = MutableStateFlow<EpisodeEntity?>(null)
    val individualEpisodeData: StateFlow<EpisodeEntity?> = _individualEpisodeData.asStateFlow()


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
                var entity = EpisodeEntity(item.guid,
                    item.podcastTitle,
                    item.title,
                    item.description,
                    item.url,
                    item.episodeNumber,
                    item.episodeSeason)
                episodeEntityArrayList.add(entity)
            }
        }
        episodeEntityList = episodeEntityArrayList
        episodeDao.insertEpisode(episodeEntityList)
        Log.d("RoomRepo", "Added the data to the room repo. ")
    }

    //Function to get individual episode data from room

    /*suspend fun getIndividualEpisodeData(epNum: String): EpisodeEntity? {
        return episodeDao.getEpisodeDetails(epNum).collect { episode ->
            _individualEpisodeData.value = episode
        }

    }*/

    /*suspend fun getAllData(): List<EpisodeEntity>{

        episodeDao.getPodcastEpisodes().collect { episodeList ->
            _episodes.value = episodeList
        }

        return episodes.value
    }
*/

}