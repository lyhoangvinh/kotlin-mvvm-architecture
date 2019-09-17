package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.source.avg.SearchDataSource
import javax.inject.Inject

class SearchPagedRepo @Inject constructor(private val searchFactory: SearchDataSource.SearchFactory, private val searchHistoryDao: SearchHistoryDao) :
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

    fun insertHistory(query: String){
        execute {
            searchHistoryDao.insert(
                SearchHistory(
                    keyword = query,
                    url = "",
                    timestamp = (System.currentTimeMillis() / 1000).toString()
                )
            )
        }
    }
}