package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.dao.VideosDao
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.itemviewmodel.SearchDataItem
import com.lyhoangvinh.simple.data.itemviewmodel.SearchHistoryItem
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.ResponseBiZip
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import javax.inject.Inject

interface SearchRepo {
    fun insertHistory(keyword: String, url: String, timestamp: String)
    fun deleteHistory(searchHistory: SearchHistory)
    fun deleteAllSearchData()
    fun mergedData(): LiveData<List<ItemViewModel>>
    fun search(
        isRefresh: Boolean,
        query: String,
        page: Int
    ): Flowable<Resource<ResponseBiZip<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>>>
}

