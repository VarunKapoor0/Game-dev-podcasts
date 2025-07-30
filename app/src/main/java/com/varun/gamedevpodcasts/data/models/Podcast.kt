package com.varun.gamedevpodcasts.data.models

data class Podcast(var link: String,
                   var imageUrl: String,
                   var name: String,
                   var details: List<Episode>)
