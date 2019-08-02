package com.lyhoangvinh.simple.data.itemviewmodel

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entinies.avgle.Video
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class VideoItem(var videos: PagedList<Video>?, override val idViewModel: String?) : ItemViewModel