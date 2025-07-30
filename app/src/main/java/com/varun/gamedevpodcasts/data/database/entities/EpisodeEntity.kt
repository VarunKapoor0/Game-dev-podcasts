package com.varun.gamedevpodcasts.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Episode")
data class EpisodeEntity(
    @PrimaryKey() var guid: String,
    @ColumnInfo(name="podcast_title") var podcastTitle: String,
    @ColumnInfo(name="episode_title") var title: String,
    @ColumnInfo(name="episode_description") var description: String,
    @ColumnInfo(name="episode_url") var url: String,
    @ColumnInfo(name="episode_number") var episodeNumber: Int?,
    @ColumnInfo(name="episode_seasons") var episodeSeason: Int? = null)