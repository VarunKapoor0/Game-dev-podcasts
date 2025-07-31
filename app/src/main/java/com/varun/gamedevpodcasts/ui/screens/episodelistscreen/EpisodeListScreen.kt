package com.varun.gamedevpodcasts.ui.screens.episodelistscreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.varun.gamedevpodcasts.viewmodels.EpisodeListViewmodel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.varun.gamedevpodcasts.navigation.Screen

@Composable
fun EpisodeListScreen(navController: NavHostController, podcastName: String){

    val viewmodel: EpisodeListViewmodel = hiltViewModel()
    //var response = viewmodel.podcastList()
    viewmodel.getEpisodesFromRoom(podcastName)
    var response = viewmodel.episodes.collectAsState()
    Log.d("EpisodeListScreen", "Inside episodeListScreen. ")

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top = 2.dp)
    ) {
        item{
            var expanded by remember { mutableStateOf(false) }
            // Placeholder list of 100 strings for demonstration
            val menuItemData = List(100) { it+1 }
            var selectedOption: Int? = null

            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    menuItemData.forEach { option ->
                        selectedOption = option
                        DropdownMenuItem(
                            text = { Text("Season $option") },
                            onClick = { viewmodel.onSeasonSelected(option, podcastName) }
                        )
                    }
                }
            }
        }

        for (values in response.value){

            item{
                Log.d("EpisodeListScreen", "The value here is : ${values.episodeNumber}")

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
