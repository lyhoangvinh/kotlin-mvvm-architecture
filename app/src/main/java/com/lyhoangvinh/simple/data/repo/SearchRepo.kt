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
import java.sql.Timestamp
import javax.inject.Inject

class SearchRepo @Inject constructor(
    private val avgleService: AvgleService,
    private val videosDao: VideosDao,
    private val searchHistoryDao: SearchHistoryDao
) : BaseRepo() {

    fun insertHistory(keyword: String, url: String, timestamp: String) {
        execute {
            searchHistoryDao.insert(
                SearchHistory(
                    keyword = keyword,
                    url = url,
                    timestamp = timestamp
                )
            )
        }
    }

    fun deleteHistory(searchHistory: SearchHistory) {
        execute { searchHistoryDao.delete(searchHistory) }
    }

    fun mergedData(): LiveData<List<ItemViewModel>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(40)
            .setEnablePlaceholders(true)
            .build()
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(
            LivePagedListBuilder(
                searchHistoryDao.liveDataFactory(),
                10
            ).build()
        ) {
            liveDataMerger.value = SearchHistoryData(it!!)
        }

        liveDataMerger.addSource(
            LivePagedListBuilder(
                videosDao.liveDataFactoryFromType(Constants.TYPE_SEARCH),
                config
            ).build()
        ) {
            liveDataMerger.value = VideoData(it!!)
        }
        val pagedList = ArrayList<ItemViewModel>()
        return Transformations.switchMap(liveDataMerger) {
            val liveData = SafeMutableLiveData<List<ItemViewModel>>()
            when (it) {
                is SearchHistoryData -> {
                    for (i in it.searchHistory.indices){
                        pagedList.add(SearchHistoryItem(it.searchHistory[i], it.searchHistory[i].toString()))
                    }
                }
                is VideoData ->{
                    for (i in it.videoItems.indices){
                        pagedList.add(SearchDataItem(it.videoItems[i]!!, it.videoItems[i].toString()))
                    }
                }
            }
            liveData.setValue(pagedList)
            return@switchMap liveData
        }
    }

    fun search(
        isRefresh: Boolean,
        query: String,
        page: Int
    ): Flowable<Resource<ResponseBiZip<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>>> {
        return createResource(isRefresh,
            avgleService.searchVideos(query, page),
            avgleService.searchJav(query, page),
            object :
                OnSaveBiResultListener<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>> {
                override fun onSave(
                    data: ResponseBiZip<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>,
                    isRefresh: Boolean
                ) {

                    val data1 = data.t1
                    val data2 = data.t1
                    val newList: ArrayList<Video> = ArrayList()
                    if (data1 != null && data1.success && data1.response.videos.isNotEmpty()) {
                        newList.addAll(data1.response.videos)
                    }
                    if (data2 != null && data2.success && data2.response.videos.isNotEmpty()) {
                        newList.addAll(data2.response.videos)
                    }

                    for (x in 0 until newList.size) {
                        newList[x].type = Constants.TYPE_SEARCH
                    }
                    if (isRefresh) {
                        execute {
                            videosDao.deleteType(Constants.TYPE_SEARCH)
                            videosDao.insertIgnore(newList)
                            videosDao.updateIgnore(newList)
                        }
                    } else {
                        execute {
                            videosDao.insertIgnore(newList)
                            videosDao.updateIgnore(newList)
                        }
                    }
                }
            })
    }
}