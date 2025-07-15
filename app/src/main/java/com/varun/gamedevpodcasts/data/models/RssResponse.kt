package com.varun.gamedevpodcasts.data.models

//TODO: This needs to be an array of the datatype Podcast.
data class RssResponse(var numberOfLinks: Int, var podcastDetails: ArrayList<Podcast>)
