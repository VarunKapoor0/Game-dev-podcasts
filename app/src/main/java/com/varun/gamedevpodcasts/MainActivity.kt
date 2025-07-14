package com.varun.gamedevpodcasts

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varun.gamedevpodcasts.ui.screens.homescreen.HomeScreen
import com.varun.gamedevpodcasts.ui.screens.podcastlistscreen.podcastListScreen
import com.varun.gamedevpodcasts.ui.theme.GameDevPodcastsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val rssList: ArrayList<String> = arrayListOf<String>()
    var inputStream: InputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //var title: String = pullingXMLData(context = applicationContext)
        setContent {
            GameDevPodcastsTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(onStartClick = { navController.navigate("podcastlist") }) }
                    composable("podcastlist") { podcastListScreen() }
                }
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = title,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }*/
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
