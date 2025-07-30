package com.varun.gamedevpodcasts.data.repositories

import android.content.Context
import android.util.Log
import com.varun.gamedevpodcasts.R
import com.varun.gamedevpodcasts.data.models.Episode
import com.varun.gamedevpodcasts.data.models.Podcast
import com.varun.gamedevpodcasts.data.models.RssResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Runnable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RSSFeedRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val rssList: ArrayList<String> = arrayListOf<String>()
    var inputStream: InputStream? = null

    fun pullingXmlParser(): ArrayList<String> {
        val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        var xmlPullParser: XmlPullParser = factory.newPullParser()
        xmlPullParser = context.resources.getXml(R.xml.rss_feeds)
        var eventType: Int = xmlPullParser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.name.equals("link")) {
                    xmlPullParser.next()
                    rssList.add(xmlPullParser.text.trim())
                }
            }
            eventType = xmlPullParser.next()
        }
        Log.d("RSSFeedRepository", "The length of the rss feed list is : ${rssList.size}")
        return rssList
    }


    //TODO: Send all the data to the room db.
    fun rssLinkData(rssList: ArrayList<String>): RssResponse {
        var response: RssResponse = RssResponse(0, arrayListOf<Podcast>())
        var podcastList: ArrayList<Podcast> = arrayListOf<Podcast>()
        //thread for url connection
        var thread = Thread(Runnable {
            val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            var xmlPullParser: XmlPullParser = factory.newPullParser()


            for (link in rssList) {
                var podcastTitleObtained = false
                var podcastImageObtained = false

                var podcastImageUrl: String = ""
                var podcastName: String = ""
                var podcast: Podcast = Podcast(link, "", "", arrayListOf<Episode>())
                var episodeList: ArrayList<Episode> = arrayListOf<Episode>()

                var titles: ArrayList<String> = arrayListOf<String>()
                var descriptions: ArrayList<String> = arrayListOf<String>()
                var epNums: ArrayList<Int> = arrayListOf<Int>()
                var seasons: ArrayList<Int?> = arrayListOf<Int?>()
                var guids: ArrayList<String> = arrayListOf<String>()
                var urls: ArrayList<String> = arrayListOf<String>()
                var podcastTitles: String = ""
                try {
                    var url = URL(link)
                    inputStream = url.openConnection().inputStream
                    xmlPullParser.setInput(inputStream, null)

                    var eventType = xmlPullParser.eventType
                    var insideItem = false
                    var insideChannel = false
                    var insideImage = false


                    var currentTitle: String? = null
                    var currentDescription: String? = null
                    var currentEpNum: Int? = null
                    var currentGuid: String? = null
                    var currentUrl: String? = null
                    var currentSeason: Int? = null


                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            var namespace = xmlPullParser.namespace
                            if (xmlPullParser.name.equals("channel")) {
                                insideChannel = true
                            } else if (xmlPullParser.name.equals("title") && insideChannel && !podcastTitleObtained) {
                                xmlPullParser.next()
                                Log.d(
                                    "Title",
                                    "The podcast title is : ${xmlPullParser.text.trim()}"
                                )
                                podcastTitles = xmlPullParser.text.trim()
                                podcastTitleObtained = true
                            } else if (xmlPullParser.name.equals("image")) {
                                insideImage = true
                            } else if (xmlPullParser.name.equals("url") && insideImage && !podcastImageObtained) {
                                xmlPullParser.next()
                                podcastImageUrl = xmlPullParser.text
                                podcastImageObtained = true

                            } else if (xmlPullParser.name.equals("item")) {
                                insideItem = true
                                currentTitle = null
                                currentDescription = null
                                currentEpNum = null
                                currentGuid = null
                                currentUrl = null
                                currentSeason = null
                            } else if (xmlPullParser.name.equals("title") && insideItem && namespace.isNullOrEmpty()) {
                                xmlPullParser.next()
                                currentTitle = xmlPullParser.text.trim()
                            } else if (xmlPullParser.name.equals("description") && insideItem) {
                                xmlPullParser.next()
                                currentDescription = xmlPullParser.text.trim()
                            } else if (xmlPullParser.name.equals(("episode")) && insideItem) {
                                xmlPullParser.next()
                                currentEpNum = xmlPullParser.text.toIntOrNull()
                            } else if (xmlPullParser.name.equals("guid") && insideItem) {
                                xmlPullParser.next()
                                currentGuid = xmlPullParser.text.trim()
                            } else if (xmlPullParser.name.equals("enclosure") && insideItem) {
                                xmlPullParser.next()
                                currentUrl = xmlPullParser.getAttributeValue(null, "url")
                            } else if (xmlPullParser.name.equals("season") && namespace.equals("http://www.itunes.com/dtds/podcast-1.0.dtd") && insideItem) {
                                xmlPullParser.next()
                                currentSeason = xmlPullParser.text.toIntOrNull()

                            }
                        }
                        if (eventType == XmlPullParser.END_TAG) {
                            if (xmlPullParser.name.equals("item")) {
                                insideItem = false
                                var currentEpisode: Episode = Episode(
                                    guid = currentGuid.toString(),
                                    podcastTitle = podcastTitles,
                                    title = currentTitle.toString(),
                                    description = currentDescription.toString(),
                                    url = currentUrl.toString(),
                                    episodeNumber = currentEpNum,
                                    episodeSeason = currentSeason
                                )
                                episodeList.add(currentEpisode)
                            } else if (xmlPullParser.name.equals("channel")) {
                                insideChannel = false
                            }

                        }
                        eventType = xmlPullParser.next()
                    }

                } catch (e: Exception) {
                    Log.e("Error", "The error is : $e")
                    e.printStackTrace()
                } finally {
                    inputStream?.close()
                }
                //add episode list to specific podcast object

                var deduplicatedEpisodeList = episodeList.distinct()
                podcast.details = deduplicatedEpisodeList
                podcast.imageUrl = podcastImageUrl
                podcast.name = podcastTitles
                Log.d("Image", "The image url is : ${podcast.imageUrl}")
                podcastList.add(podcast)
                titles.clear()
                podcastTitles = ""
                descriptions.clear()
                urls.clear()
                epNums.clear()
                seasons.clear()
                podcastTitleObtained = false
                podcastImageObtained = false
            }

        })
        thread.start()
        thread.join()
        response.numberOfLinks = rssList.size
        response.podcastDetails = podcastList
        return response
    }
}