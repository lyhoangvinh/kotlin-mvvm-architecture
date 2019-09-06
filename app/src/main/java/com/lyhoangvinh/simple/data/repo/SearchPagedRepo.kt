package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.source.avg.SearchDataSource
import javax.inject.Inject

class SearchPagedRepo @Inject constructor(private val searchFactory: SearchDataSource.SearchFactory) :
    BaseRepo() {

    fun liveVideo(): LiveData<PagedList<Video>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(100)
            .setPrefetchDistance(50)
            .build()
        return LivePagedListBuilder(searchFactory, config).build()
    }

    fun setQuery(query: String) {
        execute {
            searchFactory.setQuery(query)
            searchFactory.invalidate()
        }
    }
}