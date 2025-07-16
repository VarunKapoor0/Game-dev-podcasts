package com.varun.gamedevpodcasts.data

import android.content.Context
import androidx.room.Room
import com.varun.gamedevpodcasts.data.database.dao.EpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "podcast_database")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun getEpisodeDao(database: AppDatabase): EpisodeDao{
        return database.episodeDao()
    }
}