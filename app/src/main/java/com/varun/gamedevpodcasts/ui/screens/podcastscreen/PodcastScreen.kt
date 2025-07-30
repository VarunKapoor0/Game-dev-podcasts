package com.varun.gamedevpodcasts.ui.screens.podcastscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.varun.gamedevpodcasts.navigation.Screen
import com.varun.gamedevpodcasts.viewmodels.PodcastViewModel

@Composable
fun PodcastScreen(navController: NavHostController) {

    val viewmodel: PodcastViewModel = hiltViewModel()

    val response = viewmodel.podcastDetails.collectAsState()
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("The name of the podcast is : ${response.value?.name}")

        Button(onClick = {navController.navigate(Screen.EpisodeList.route + "/${response.value?.name}")}) {
            Text("See Episodes for ${response.value?.name}")
        }
    }




}