package com.varun.gamedevpodcasts.ui.screens.episodeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varun.gamedevpodcasts.viewmodels.EpisodeViewModel

@Composable
fun EpisodeScreen() {

    val viewModel: EpisodeViewModel = hiltViewModel()
    var response = viewModel.individualEpisodeData.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("${response.value?.title}")
        Text(buildAnnotatedString {
            append("Listen ")
            withLink(LinkAnnotation.Url(response.value?.url.toString(), TextLinkStyles(style = SpanStyle(color = Color.Blue)))) {
                append("here")
            }
        })
    }
}