package com.varun.gamedevpodcasts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity

@Database(entities = [EpisodeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun episodeDao(): EpisodeDao

    /*TODO: Create db object to access db and start using dao. */
}