package com.varun.gamedevpodcasts.ui.screens.podcastscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.varun.gamedevpodcasts.ui.screens.episodelistscreen.EpisodeListScreen
import com.varun.gamedevpodcasts.ui.screens.podcastscreen.tabnavigation.PodcastTab
import com.varun.gamedevpodcasts.viewmodels.PodcastViewModel

@Composable
fun PodcastScreen(navController: NavHostController) {

    val viewmodel: PodcastViewModel = hiltViewModel()
    var selectedTab by remember{mutableStateOf(PodcastTab.Overview)}
    val response = viewmodel.podcastDetails.collectAsState()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(response.value?.imageUrl)
            .build())
    Column(modifier = Modifier.fillMaxSize().padding(top= 48.dp, start = 6.dp, end = 6.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painter,
                modifier = Modifier.size(128.dp),
                contentDescription = "Podcast Image")
            Text(text = response.value?.name.toString(),
                textAlign = TextAlign.Center)

        }
        TabRow(selectedTabIndex = selectedTab.ordinal,
            modifier = Modifier.fillMaxWidth()) {
            PodcastTab.entries.forEachIndexed { index, tab ->
                Tab(selected = selectedTab.ordinal == index,
                    onClick = {selectedTab = tab},
                    text = {Text(tab.title)})
            }
        }

        when(selectedTab){
            PodcastTab.Overview -> OverviewTabContent(response.value?.description)
            PodcastTab.Episodes -> EpisodesTabContent(navController,
                response.value?.name.toString()
            )
        }
    }
}

@Composable
fun OverviewTabContent(description: String?){
    Text(
        AnnotatedString.fromHtml(
            description.toString()
        )
    )

}

@Composable
fun EpisodesTabContent(navController: NavHostController, podcastName: String){
    EpisodeListScreen(navController, podcastName)
    /*Button(onClick = {navController.navigate(Screen.EpisodeList.route + "/${response.value?.name}")}) {
        Text("See Episodes for ${response.value?.name}")
    }*/
}