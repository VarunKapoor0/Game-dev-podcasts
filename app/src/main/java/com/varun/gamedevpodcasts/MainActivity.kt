package com.varun.gamedevpodcasts

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.varun.gamedevpodcasts.data.RSSFeedRepository
import com.varun.gamedevpodcasts.ui.theme.GameDevPodcastsTheme
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pullingXMLData(context = applicationContext)
        setContent {
            GameDevPodcastsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    val rssList: ArrayList<String> = arrayListOf<String>()

    //TODO: Obtain list of sml links from an xml file stored in assets folder.(currently, using xmlpullparser).

    public fun pullingXMLData(context: Context){
        val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        var xmlPullParser: XmlPullParser = factory.newPullParser()

        xmlPullParser = context.resources.getXml(R.xml.rss_feeds)
        //xmlPullParser.setInput(inputStream, null)

        var eventType: Int = xmlPullParser.eventType
        Log.d("RSSFeedRepository", "Before the while loop. ${xmlPullParser.name} ")

        while(eventType != XmlPullParser.END_DOCUMENT){
            Log.d("RSSFeedRepository", "Before Start Tag check. ")

            if(eventType == XmlPullParser.START_TAG){
                if(xmlPullParser.name.equals("link")){
                    xmlPullParser.next()
                    rssList.add(xmlPullParser.text.trim())
                    Log.d("RSSFeedRepository", "The links are getting added to the arrayilist. ")
                    //rssList.add(xmlPullParser.getAttributeValue(0))
                }
            }
            eventType = xmlPullParser.next()
        }
        //Log.d("RSSFeedRepository", "Done. ")
        Log.d("RSSFeedRepository", "The length of the rss feed list is : $rssList")
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