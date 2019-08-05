package com.lyhoangvinh.simple.data.itemviewmodel

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class CollectionBottomItem(var collections: PagedList<Collection>?, override val idViewModel: String?) : ItemViewModel
