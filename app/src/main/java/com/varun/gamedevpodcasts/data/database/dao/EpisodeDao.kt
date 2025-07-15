package com.varun.gamedevpodcasts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM EpisodeEntity")
    fun getAllEpisodes(): ArrayList<EpisodeEntity>

    @Insert
    fun insertEpisode(vararg episodeEntity: EpisodeEntity)
}