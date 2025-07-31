package com.varun.gamedevpodcasts.data.repositories

import android.util.Log
import com.varun.gamedevpodcasts.data.database.dao.PodcastDao
import com.varun.gamedevpodcasts.data.database.entities.PodcastEntity
import kotlinx.coroutines.Runnable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class PodcastRepository @Inject constructor(
    private val podcastDao: PodcastDao,
    private val repository: RSSFeedRepository) {

    var inputStream: InputStream? = null


    suspend fun setPodcastDetails(){
        var links = repository.pullingXmlParser()
        var podcastList: ArrayList<PodcastEntity> = arrayListOf<PodcastEntity>()

        var thread = Thread(Runnable {
            try{
                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                var xmlPullParser: XmlPullParser = factory.newPullParser()
                var id = 0
                for(link in links){
                    var podcastTitleObtained = false
                    var podcastImageObtained = false
                    var podcastDescriptionObtained = false

                    var url = URL(link)
                    inputStream = url.openConnection().inputStream
                    xmlPullParser.setInput(inputStream, null)

                    var eventType = xmlPullParser.eventType
                    var name = ""
                    var imageUrl = ""
                    var description = ""

                    var insideChannel = false
                    var insideImage = false

                    while(eventType != XmlPullParser.END_DOCUMENT){
                        if(eventType == XmlPullParser.START_TAG) {
                            var namespace = xmlPullParser.namespace
                            if (xmlPullParser.name.equals("channel")) {
                                insideChannel = true
                            } else if (xmlPullParser.name.equals("title") && insideChannel && !podcastTitleObtained) {
                                xmlPullParser.next()
                                name = xmlPullParser.text.trim()
                                podcastTitleObtained = true
                            }
                            else if(xmlPullParser.name.equals("description") && insideChannel && !podcastDescriptionObtained){
                                xmlPullParser.next()
                                description = xmlPullParser.text
                                podcastDescriptionObtained = true
                            }
                            else if (xmlPullParser.name.equals("image")) {
                                insideImage = true
                            } else if (xmlPullParser.name.equals("url") && insideImage && !podcastImageObtained) {
                                xmlPullParser.next()
                                imageUrl = xmlPullParser.text
                                podcastImageObtained = true
                            }

                        }
                        if(eventType == XmlPullParser.END_TAG){
                            if(xmlPullParser.name.equals("channel")){
                                insideChannel = false
                            } else if(xmlPullParser.name.equals("image")){
                                insideImage = false
                            }
                        }
                        eventType = xmlPullParser.next()
                    }
                    podcastTitleObtained = false
                    podcastImageObtained = false
                    podcastDescriptionObtained = false
                    id++
                    var podcastEntity = PodcastEntity(id, name, imageUrl, description)
                    podcastList.add(podcastEntity)
                }
            } catch (e: Exception){
                Log.e("Exception", "The error is : $e")
            }



        })
        thread.start()
        thread.join()
        podcastDao.insertPodcasts(podcastList)
    }
}