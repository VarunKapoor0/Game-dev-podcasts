package com.varun.gamedevpodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.varun.gamedevpodcasts.navigation.Screen
import com.varun.gamedevpodcasts.ui.screens.episodeScreen.EpisodeScreen
import com.varun.gamedevpodcasts.ui.screens.homescreen.HomeScreen
import com.varun.gamedevpodcasts.ui.screens.episodelistscreen.EpisodeListScreen
import com.varun.gamedevpodcasts.ui.screens.podcastlistscreen.PodcastListScreen
import com.varun.gamedevpodcasts.ui.theme.GameDevPodcastsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameDevPodcastsTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) { HomeScreen(onStartClick = { navController.navigate(Screen.PodcastList.route) }) }
                    composable(Screen.PodcastList.route){ PodcastListScreen(navController)}
                    composable(Screen.EpisodeList.route + "/{podcast_name}", arguments = listOf(navArgument("podcast_name") {type =
                    NavType.StringType})) { EpisodeListScreen(navController) }
                    composable(Screen.Episode.route + "/{guid}", arguments = listOf(navArgument("guid") { type =
                        NavType.StringType })) {EpisodeScreen()}
                }
            }
        }
    }
}

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        GameDevPodcastsTheme {
            Greeting("Android")
        }
    }
