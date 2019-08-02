package com.lyhoangvinh.simple.data.itemviewmodel

import com.lyhoangvinh.simple.data.entinies.avgle.Collection
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

data class CollectionBannerItem(var collections: List<Collection>?, override val idViewModel: String?):ItemViewModel