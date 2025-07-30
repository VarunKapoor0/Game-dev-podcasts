package com.varun.gamedevpodcasts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.dao.PodcastDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import com.varun.gamedevpodcasts.data.database.entities.PodcastEntity

@Database(entities = [EpisodeEntity::class, PodcastEntity::class], version = 9, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun episodeDao(): EpisodeDao
    abstract fun podcastDao(): PodcastDao

    /*TODO: Create db object to access db and start using dao. */

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

    }
}