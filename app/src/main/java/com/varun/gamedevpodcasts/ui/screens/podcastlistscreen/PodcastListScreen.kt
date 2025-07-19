package com.varun.gamedevpodcasts.ui.screens.podcastlistscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.varun.gamedevpodcasts.navigation.Screen
import com.varun.gamedevpodcasts.viewmodels.EpisodeListViewmodel
import com.varun.gamedevpodcasts.viewmodels.PodcastListViewModel

@Composable
fun PodcastListScreen(navController: NavHostController) {

    var viewmodel: PodcastListViewModel = hiltViewModel()
    var response = viewmodel.podcastNames.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(48.dp)
    ) {

        for (value in response.value){
            item{
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    modifier = Modifier.fillMaxWidth().padding(12.dp).clickable{navController.navigate(
                        Screen.EpisodeList.route + "/${value}")}
                ){
                    Text(text = "Title: $value", modifier = Modifier.padding(12.dp))

                }
            }

        }
    }

}