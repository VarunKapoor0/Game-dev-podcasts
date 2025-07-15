package com.varun.gamedevpodcasts.models

//TODO: This needs to be an array of the datatype Podcast.
data class RssResponse(var numberOfLinks: Int, var podcastDetails: ArrayList<Podcast>)
