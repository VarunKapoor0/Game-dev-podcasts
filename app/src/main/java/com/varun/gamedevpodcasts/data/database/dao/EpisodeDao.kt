package com.varun.gamedevpodcasts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM Episode ORDER BY guid ASC")
    fun getAllEpisodes(): Flow<List<EpisodeEntity>>

    @Insert
    suspend fun insertEpisode(episodeEntity: List<EpisodeEntity>)
}