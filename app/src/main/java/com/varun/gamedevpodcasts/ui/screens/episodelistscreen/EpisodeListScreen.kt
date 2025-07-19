package com.varun.gamedevpodcasts.ui.screens.episodelistscreen

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
import com.varun.gamedevpodcasts.viewmodels.EpisodeListViewmodel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.varun.gamedevpodcasts.navigation.Screen

@Composable
fun EpisodeListScreen(navController: NavHostController){

    val viewmodel: EpisodeListViewmodel = hiltViewModel()
    //var response = viewmodel.podcastList()
    var response = viewmodel.episodes.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(48.dp)
    ) {

        for (values in response.value){
            item{

                //TODO: Every card, on click, can send the episode number as an argument and then in the next page,
                // i can pick up that episode number's details via room.
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    modifier = Modifier.fillMaxWidth().padding(12.dp).clickable{navController.navigate(
                        Screen.Episode.route + "/${values.guid}")}
                ){
                    Text(text = "Episode number is : ${values.episodeNumber}", modifier = Modifier.padding(12.dp))
                    Text(text = "Title: ${values.title}", modifier = Modifier.padding(12.dp))

                    /*TODO: Every time a card is clicked and the user navigates back, due to to the fact that there
                    *  is so much data from the repository which is incoming, the app lags. Also, the data keeps
                    *  adding on top of the previous response, hence, it is a HORRIBLE way to do things.
                    *  1. Todo is to integrate room in the app for ease of data accessibility, and less overhead. -  Done.
                    *  2. Next TODO: is to seperate network calls from the repository to a seperate layer.
                    *
                    * */
                    //Text("Description: ${value.description}") to be added later
                }
            }

        }
    }
}
