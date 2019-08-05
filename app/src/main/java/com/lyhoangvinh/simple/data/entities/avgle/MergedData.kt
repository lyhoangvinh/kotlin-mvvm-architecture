package com.lyhoangvinh.simple.data.entities.avgle

import androidx.paging.PagedList

sealed class MergedData
data class SearchData(val name: String) : MergedData()
data class CategoryData(val categoryItems: PagedList<Category>) : MergedData()
data class CollectionBannerData(val collectionBannerItems: List<Collection>) : MergedData()
data class CollectionBottomData(val collectionBottomItems: PagedList<Collection>) : MergedData()
data class VideoData(val videoItems: PagedList<Video>) : MergedData()