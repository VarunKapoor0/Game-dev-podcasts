package com.varun.gamedevpodcasts.data

import android.content.Context
import android.util.Log
import com.varun.gamedevpodcasts.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class RSSFeedRepository {

    /*val rssList: ArrayList<String> = arrayListOf<String>()

    //TODO: Obtain list of sml links from an xml file stored in assets folder.(currently, using xmlpullparser).

    public fun pullingXMLData(context: Context){
        val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val xmlPullParser: XmlPullParser = factory.newPullParser()

        val inputStream: InputStream = context.resources.getXml(R.xml.rss_feeds) as InputStream
        xmlPullParser.setInput(inputStream, null)

        var eventType: Int = xmlPullParser.eventType

        while(eventType!== XmlPullParser.END_DOCUMENT){
            if(eventType== XmlPullParser.START_TAG){
                if(xmlPullParser.name.equals("feed")){
                    rssList.add(xmlPullParser.getAttributeValue(0))
                }
            }
            xmlPullParser.next()
        }
        Log.d("RSSFeedRepository", "The length of the rss feed list is : ${rssList.size}")
    }*/
}