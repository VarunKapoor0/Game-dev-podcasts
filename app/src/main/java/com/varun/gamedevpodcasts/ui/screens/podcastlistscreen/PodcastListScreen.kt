package com.varun.gamedevpodcasts.ui.screens.podcastlistscreen

import android.icu.lang.UCharacter
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.varun.gamedevpodcasts.viewmodels.PodcastListViewmodel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun podcastListScreen(){

    val viewmodel: PodcastListViewmodel = hiltViewModel()
    Log.d("PodcastListScreen", "The screen is about to run the podcastList() function. ")
    var response = viewmodel.podcastList()
    Log.d("PodcastListScreen", "The screen has run the podcastList() function. ")

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(64.dp)
    ) {
        item{
            Card { Text("${response.size}") }
        }
        for (values in response){
            item{
                Card { Text("number of episodes: ${values.details.size}") }
            }
            for(value in values.details){
                item{
                    Card(
                        modifier = Modifier.padding(32.dp)
                    ){
                        Text("Title: ${value.title}")
                        Text("Description: ${value.description}")
                        Spacer(Modifier.height(64.dp))
                    }
                }


            }
        }
    }
}
