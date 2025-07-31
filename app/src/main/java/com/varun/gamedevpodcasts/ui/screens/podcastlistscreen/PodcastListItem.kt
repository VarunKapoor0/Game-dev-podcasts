package com.varun.gamedevpodcasts.ui.screens.podcastlistscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun PodcastListItem(modifier: Modifier, imageUrl: String, podcastName: String) {

    Log.d("Image", "The img url is : $imageUrl")
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .build())
    Row(modifier = modifier) {
        Image(painter = painter,
            modifier = Modifier.size(64.dp),
            contentDescription = "Podcast Image")
        Text(text=podcastName,
            fontSize = 12.sp,
            modifier = Modifier.padding(12.dp))
    }
}