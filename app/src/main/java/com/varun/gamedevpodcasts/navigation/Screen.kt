package com.varun.gamedevpodcasts.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object PodcastList: Screen("podcastlist")
    object Podcast: Screen("podcast")
    object EpisodeList: Screen("episodelist")
    object Episode: Screen("episode")


}