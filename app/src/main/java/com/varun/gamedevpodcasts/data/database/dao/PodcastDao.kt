package com.varun.gamedevpodcasts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varun.gamedevpodcasts.data.database.entities.PodcastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {

    @Query("SELECT * FROM Podcast")
    fun getAllPodcasts(): Flow<List<PodcastEntity>>

    @Query("SELECT * FROM Podcast WHERE name= :name")
    fun getPodcast(name: String?): Flow<PodcastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPodcasts(podcastEntity: List<PodcastEntity>)
}