package com.lyhoangvinh.simple.data.repo.impl

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.repo.BaseRepo
import com.lyhoangvinh.simple.data.repo.SearchPagedRepo
import com.lyhoangvinh.simple.data.source.avg.SearchDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchPagedRepoImpl @Inject constructor(
    private val searchFactory: SearchDataSource.SearchFactory,
    private val searchHistoryDao: SearchHistoryDao
) : SearchPagedRepo, BaseRepo() {
    override fun rxFetchData(compositeDisposable: CompositeDisposable): MediatorLiveData<MergedData> {
        val stateLiveData = SafeMutableLiveData<State>()
        val emptyLiveData = SafeMutableLiveData<DataEmpty>()
        return MediatorLiveData<MergedData>().apply {
            addSource(emptyLiveData) {
                value = EmptyMergerdData(it)
            }
            addSource(LivePagedListBuilder(searchFactory.apply {
                setCompositeDisposable(compositeDisposable)
                setStateLiveData(stateLiveData)
                setEmptyLiveData(emptyLiveData)
            }, PagedList.Config.Builder().apply {
                setPageSize(50).setEnablePlaceholders(true).setInitialLoadSizeHint(100)
                    .setPrefetchDistance(50)
            }.build()).build()) {
                value = VideoData(it)
            }
            addSource(stateLiveData) {
                value = StateData(it)
            }
        }
    }

    override fun setQuery(query: String) {
        execute {
            searchFactory.setQuery(query)
            searchFactory.invalidate()
            //insertHistory(query)
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