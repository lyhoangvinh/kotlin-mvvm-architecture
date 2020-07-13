package com.lyhoangvinh.simple.data.repo.impl

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
import com.lyhoangvinh.simple.data.repo.BaseRepo
import com.lyhoangvinh.simple.data.repo.SearchRepo
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.ResponseBiZip
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val avgleService: AvgleService,
    private val videosDao: VideosDao,
    private val searchHistoryDao: SearchHistoryDao
) : SearchRepo, BaseRepo() {

    override fun insertHistory(keyword: String, url: String, timestamp: String) {
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

    override fun deleteHistory(searchHistory: SearchHistory) {
        execute { searchHistoryDao.delete(searchHistory) }
    }

    override fun deleteAllSearchData() {
        execute {
            videosDao.deleteType(Constants.TYPE_SEARCH)
        }
    }

    override fun mergedData(): LiveData<List<ItemViewModel>> {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(true)
            .build()
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(
            LivePagedListBuilder(
                searchHistoryDao.liveDataFactory(),
                config
            ).build()
        ) {
            liveDataMerger.value = SearchHistoryData(it!!)
        }

        liveDataMerger.addSource(videosDao.liveDataFromType(Constants.TYPE_SEARCH)) {
            liveDataMerger.value = SearchData(it!!)
        }

        val pagedList = ArrayList<ItemViewModel>()
        return Transformations.switchMap(liveDataMerger) {
            val liveData = SafeMutableLiveData<List<ItemViewModel>>()
            when (it) {
                is SearchHistoryData -> {
                    for (i in it.searchHistory.indices) {
                        pagedList.add(
                            SearchHistoryItem(
                                it.searchHistory[i],
                                it.searchHistory[i].toString()
                            )
                        )
                    }
                }
                is SearchData -> {
                    for (i in it.videoItems.indices) {
                        pagedList.add(
                            SearchDataItem(
                                it.videoItems[i],
                                it.videoItems[i].toString()
                            )
                        )
                    }
                }
            }
            liveData.setValue(pagedList)
            return@switchMap liveData
        }
    }

    override fun search(
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
                    val newList = arrayListOf<Video>()
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