package com.example.rsslab

import android.content.ClipData


data class RSSObject(
 val status: String,
 val feed: Feed,
// val items: List<ClipData.c>
 val items: List<RSSItem>

)