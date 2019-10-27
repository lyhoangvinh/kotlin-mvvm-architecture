package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.source.avg.SearchDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import javax.inject.Inject

class SearchPagedRepo @Inject constructor(private val searchFactory: SearchDataSource.SearchFactory, private val searchHistoryDao: SearchHistoryDao) :
    BaseRepo() {

    fun liveVideo(stateLiveData :SafeMutableLiveData<State>, emptyLiveData : SafeMutableLiveData<DataEmpty>): LiveData<PagedList<Video>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(100)
            .setPrefetchDistance(50)
            .build()
        searchFactory.setStateLiveData(stateLiveData)
        searchFactory.setEmptyLiveData(emptyLiveData)
        return LivePagedListBuilder(searchFactory, config).build()
    }

    fun rxFetchData(): MediatorLiveData<MergedData> {
        val liveDataMerger = MediatorLiveData<MergedData>()
        val stateLiveData = SafeMutableLiveData<State>()
        val emptyLiveData = SafeMutableLiveData<DataEmpty>()
        liveDataMerger.addSource(emptyLiveData){
            liveDataMerger.value = EmptyMergerdData(it)
        }
        liveDataMerger.addSource(liveVideo(stateLiveData, emptyLiveData)) {
            liveDataMerger.value = VideoData(it)
        }
        liveDataMerger.addSource(stateLiveData) {
            liveDataMerger.value = StateData(it)
        }
        return liveDataMerger
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