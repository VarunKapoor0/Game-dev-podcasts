package com.varun.gamedevpodcasts.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Podcast")
data class PodcastEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image_url") var imageUrl: String,
    @ColumnInfo(name = "description") var description: String)