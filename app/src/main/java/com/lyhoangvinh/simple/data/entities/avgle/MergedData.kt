package com.lyhoangvinh.simple.data.entities.avgle

import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State

sealed class MergedData
data class SearchData(val name: String) : MergedData()
data class CategoryData(val categoryItems: PagedList<Category>) : MergedData()
data class CollectionBannerData(val collectionBannerItems: List<Collection>) : MergedData()
data class CollectionBottomData(val collectionBottomItems: PagedList<Collection>) : MergedData()
data class VideoData(val videoItems: PagedList<Video>) : MergedData()
data class StateData(val state: State) : MergedData()
data class CollectionData(val collections: PagedList<Collection>) : MergedData()
data class SearchHistoryData(val searchHistory: List<SearchHistory>) : MergedData()