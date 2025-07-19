package com.varun.gamedevpodcasts.data.models

data class Episode(var guid: String,
                   var podcastTitle: String,
                   var title: String,
                   var description: String,
                   var url: String,
                   var episodeNumber: Int,
                   var episodeSeason: Int? = null)
