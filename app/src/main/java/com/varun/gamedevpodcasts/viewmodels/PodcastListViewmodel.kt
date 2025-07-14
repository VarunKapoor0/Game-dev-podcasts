package com.varun.gamedevpodcasts.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varun.gamedevpodcasts.data.RSSFeedRepository
import com.varun.gamedevpodcasts.models.RssResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastListViewmodel @Inject constructor(
    private val repository: RSSFeedRepository
): ViewModel() {

    private val _title = MutableStateFlow<List<String>>(emptyList())
    val title: StateFlow<List<String>> = _title

    private val _description = MutableStateFlow<List<String>>(emptyList())
    val description: StateFlow<List<String>> = _description

    init{

    }

    fun podcastList(){
        viewModelScope.launch {
            var rssLinks: ArrayList<String> = arrayListOf<String>()
            rssLinks = repository.pullingXmlParser()
            var responseList: ArrayList<RssResponse> = arrayListOf<RssResponse>()
            responseList = repository.rssLinkData(rssLinks)
            Log.d("Viewmodel", "The data from the viewmodel is : $responseList")
        }
    }

    /*TODO: Send individual card data to the UI. Each link has multiple podcast episodes under it,
    *  therefore it has to be a list. Already getting responseList, but it contains all the details of all episodes from
    *  all links. Need more nesting in the model. MODEL HAS TO BE CHANGED.
    */
}