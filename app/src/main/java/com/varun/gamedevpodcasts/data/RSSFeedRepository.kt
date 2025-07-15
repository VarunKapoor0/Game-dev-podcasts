package com.varun.gamedevpodcasts.data

import android.content.Context
import android.util.Log
import com.varun.gamedevpodcasts.R
import com.varun.gamedevpodcasts.models.Episode
import com.varun.gamedevpodcasts.models.Podcast
import com.varun.gamedevpodcasts.models.RssResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Runnable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.arrayListOf

@Singleton
class RSSFeedRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val rssList: ArrayList<String> = arrayListOf<String>()
    var inputStream: InputStream? = null

    fun pullingXmlParser(): ArrayList<String>{
        val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        var xmlPullParser: XmlPullParser = factory.newPullParser()
        xmlPullParser = context.resources.getXml(R.xml.rss_feeds)
        var eventType: Int = xmlPullParser.eventType

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType== XmlPullParser.START_TAG){
                if(xmlPullParser.name.equals("link")){
                    xmlPullParser.next()
                    rssList.add(xmlPullParser.text.trim())
                }
            }
            eventType = xmlPullParser.next()
        }
        Log.d("RSSFeedRepository", "The length of the rss feed list is : ${rssList.size}")
        return rssList
    }

    fun rssLinkData(rssList: ArrayList<String>): RssResponse {
        var response: RssResponse = RssResponse(0, arrayListOf<Podcast>())
        var podcastList: ArrayList<Podcast> = arrayListOf<Podcast>()
        //thread for url connection
         var thread = Thread(Runnable {
            val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            var xmlPullParser: XmlPullParser = factory.newPullParser()


            for(link in rssList){
                var podcast: Podcast = Podcast(link, arrayListOf<Episode>())
                var episodeList: ArrayList<Episode> = arrayListOf<Episode>()
                var titles: ArrayList<String> = arrayListOf<String>()
                var descriptions: ArrayList<String> = arrayListOf<String>()
                try {
                    var url = URL(link)
                    inputStream = url.openConnection().inputStream
                    xmlPullParser.setInput(inputStream, null)

                    var eventType = xmlPullParser.eventType
                    var insideItem = false
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if(xmlPullParser.name.equals("item")){
                                insideItem=true
                            }
                            else if (xmlPullParser.name.equals("title") && insideItem) {
                                xmlPullParser.next()
                                titles.add(xmlPullParser.text.trim())
                            } else if (xmlPullParser.name.equals("description") && insideItem) {
                                xmlPullParser.next()
                                descriptions.add(xmlPullParser.text.trim())
                            }
                        }
                        if(eventType== XmlPullParser.END_TAG){
                            if(xmlPullParser.name.equals("item")){
                                insideItem = false

                            }

                        }
                        eventType = xmlPullParser.next()
                    }
                    for(index in 0..titles.size){
                        var episode: Episode = Episode(titles[index], descriptions[index])
                        episodeList.add(episode)
                    }
                }
                catch(e: Exception){
                    Log.e("Error", "The error is : $e")
                    e.printStackTrace()
                } finally{
                    inputStream?.close()
                }
                //add episode list to specific podcast object

                podcast.details = episodeList
                podcastList.add(podcast)
            }

        })
        thread.start()
        thread.join()
        response.numberOfLinks = rssList.size
        response.podcastDetails = podcastList
        return response
    }
}