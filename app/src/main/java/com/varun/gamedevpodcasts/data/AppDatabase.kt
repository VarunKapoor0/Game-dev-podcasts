package com.varun.gamedevpodcasts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import com.varun.gamedevpodcasts.data.database.entities.EpisodeEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Database(entities = [EpisodeEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun episodeDao(): EpisodeDao

    /*TODO: Create db object to access db and start using dao. */

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        /*@Provides
        @Singleton
        fun getDatabase(context: Context): AppDatabase? {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "podcast_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }*/
    }
}