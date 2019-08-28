package com.lyhoangvinh.simple.data.itemviewmodel

import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class SearchHistoryItem(var data: SearchHistory, override val idViewModel: String?) :ItemViewModel