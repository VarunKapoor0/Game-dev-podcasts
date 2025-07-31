package com.varun.gamedevpodcasts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
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
import com.varun.gamedevpodcasts.ui.screens.podcastscreen.PodcastScreen
import com.varun.gamedevpodcasts.ui.theme.GameDevPodcastsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var splashScreenTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            splashScreenTime
        }

        lifecycleScope.launch {
            delay(3000)
            splashScreenTime = false
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameDevPodcastsTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = Screen.PodcastList.route) {
                    //composable(Screen.Home.route) { HomeScreen(onStartClick = { navController.navigate(Screen.PodcastList.route) }) }
                    composable(Screen.PodcastList.route){ PodcastListScreen(navController)}
                    composable(Screen.Podcast.route + "/{p_name}",
                        arguments = listOf(navArgument("p_name") {
                            type = NavType.StringType})){ PodcastScreen(navController) }
                    composable(Screen.EpisodeList.route + "/{podcast_name}",
                        arguments = listOf(navArgument("podcast_name") {type =
                    NavType.StringType})) { EpisodeListScreen(navController, "") }
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
