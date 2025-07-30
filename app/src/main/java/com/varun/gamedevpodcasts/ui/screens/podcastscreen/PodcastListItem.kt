package com.varun.gamedevpodcasts.ui.screens.podcastscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.varun.gamedevpodcasts.R

@Composable
fun PodcastListItem(modifier: Modifier, imageUrl: String, podcastName: String) {

    Row(modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Podcast Image")
        Text(text=podcastName,
            modifier = Modifier.padding(12.dp))
    }


}