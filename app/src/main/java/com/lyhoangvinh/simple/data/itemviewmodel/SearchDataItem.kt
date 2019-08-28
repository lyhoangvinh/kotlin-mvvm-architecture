package com.lyhoangvinh.simple.data.itemviewmodel

import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class SearchDataItem(val video: Video, override val idViewModel: String?) : ItemViewModel