package com.varun.gamedevpodcasts.ui.screens.podcastlistscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.varun.gamedevpodcasts.navigation.Screen
import com.varun.gamedevpodcasts.viewmodels.PodcastListViewModel

@Composable
fun PodcastListScreen(navController: NavHostController) {

    var viewmodel: PodcastListViewModel = hiltViewModel()
    var response = viewmodel.podcastNames.collectAsState()
    var nonDBresponse = viewmodel.podcastDetails.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top = 48.dp, start = 6.dp, end = 6.dp)
    ) {
        for(values in nonDBresponse.value){
            item{
                PodcastListItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .background(color = Color.LightGray)
                        .clickable(onClick = {navController.navigate(
                            Screen.Podcast.route + "/${values.name}")}),
                    values.imageUrl,
                    values.name)
            }
        }
    }

}