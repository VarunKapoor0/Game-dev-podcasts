package com.varun.gamedevpodcasts.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object PodcastList: Screen("podcastlist")
    object Episode: Screen("episode")


}