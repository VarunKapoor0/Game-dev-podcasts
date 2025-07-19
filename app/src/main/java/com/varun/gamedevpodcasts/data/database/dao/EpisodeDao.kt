package com.varun.gamedevpodcasts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    /*@Query("SELECT * FROM Episode ORDER BY guid ASC")
    fun getAllEpisodes(): Flow<List<EpisodeEntity>>*/

    @Query("SELECT * FROM Episode WHERE podcast_title = :podcastName ORDER BY episode_number DESC ")
    fun getPodcastEpisodes(podcastName: String?): Flow<List<EpisodeEntity>>

    @Query("SELECT DISTINCT podcast_title from Episode")
    fun getPodcastNames(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episodeEntity: List<EpisodeEntity>)

    @Query("DELETE FROM Episode")
    suspend fun delete()

    @Query("SELECT * FROM Episode where guid = :guid")
    fun getEpisodeDetails(guid: String?): Flow<EpisodeEntity?>

    @Query("DELETE from Episode WHERE guid NOT IN(SELECT MAX(guid) from Episode GROUP BY episode_title)")
    suspend fun deleteDuplicateEpisodes()
}