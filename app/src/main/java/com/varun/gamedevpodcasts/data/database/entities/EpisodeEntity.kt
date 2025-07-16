package com.varun.gamedevpodcasts.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Episode")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true) var guid: Int = 0,
    @ColumnInfo(name="episode_title") var title: String,
    @ColumnInfo(name="episode_description") var description: String,
    @ColumnInfo(name="episode_number") var episodeNumber: String)