package com.lyhoangvinh.simple.data.itemviewmodel

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entinies.avgle.Collection
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class CollectionBannerItem(var collections: PagedList<Collection>):ItemViewModel