package com.varun.gamedevpodcasts.data

import android.content.Context
import android.util.Log
import com.varun.gamedevpodcasts.R
import com.varun.gamedevpodcasts.models.RssResponse
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

    fun rssLinkData(rssList: ArrayList<String>): ArrayList<RssResponse>{
        var responseList: ArrayList<RssResponse> = arrayListOf<RssResponse>()

         var thread = Thread(Runnable {
            val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            var xmlPullParser: XmlPullParser = factory.newPullParser()

            for(link in rssList){
                try {
                    var url = URL(link)
                    inputStream = url.openConnection().inputStream
                    xmlPullParser.setInput(inputStream, null)

                    var eventType = xmlPullParser.eventType
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        var response: RssResponse = RssResponse("", "")
                        if (eventType == XmlPullParser.START_TAG) {
                            if (xmlPullParser.name.equals("title")) {
                                xmlPullParser.next()
                                response.title = xmlPullParser.text.trim()
                            } else if (xmlPullParser.name.equals("description")) {
                                xmlPullParser.next()
                                response.description = xmlPullParser.text.trim()
                            }
                        }

                        responseList.add(response)
                        eventType = xmlPullParser.next()
                    }
                }
                catch(e: Exception){
                    Log.e("Error", "The error is : $e")
                    e.printStackTrace()
                } finally{
                    inputStream?.close()
                }
            }

        })
        thread.start()
        thread.join()
        return responseList
    }
}