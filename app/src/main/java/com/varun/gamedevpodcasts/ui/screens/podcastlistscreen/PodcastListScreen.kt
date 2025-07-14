package com.varun.gamedevpodcasts.ui.screens.podcastlistscreen

import android.util.Log
import androidx.compose.runtime.Composable
import com.varun.gamedevpodcasts.viewmodels.PodcastListViewmodel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun podcastListScreen(){

    val viewmodel: PodcastListViewmodel = hiltViewModel()
    Log.d("PodcastListScreen", "The screen is about to run the podcastList() function. ")
    viewmodel.podcastList()
    Log.d("PodcastListScreen", "The screen has run the podcastList() function. ")

}
