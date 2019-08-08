package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.entities.avgle.CollectionData
import com.lyhoangvinh.simple.data.entities.avgle.MergedData
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.source.avg.CollectionDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CollectionsRepo @Inject constructor(private val factory: CollectionDataSource.CollectionFactory) {

    private lateinit var liveData: LiveData<PagedList<Collection>>

    private lateinit var stateLiveData: SafeMutableLiveData<State>
    private lateinit var mCompositeDisposable: CompositeDisposable

    fun setUpRepo(stateLiveData: SafeMutableLiveData<State>, mCompositeDisposable: CompositeDisposable) {
        this.stateLiveData = stateLiveData
        this.mCompositeDisposable = mCompositeDisposable
        factory.setSateLiveSource(stateLiveData, mCompositeDisposable)
    }

    private fun liveData(): LiveData<PagedList<Collection>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(100)
            .setPrefetchDistance(50)
            .build()
        liveData = LivePagedListBuilder(factory, config).build()
        return liveData
    }

    fun feachData(): MediatorLiveData<MergedData> {
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(liveData()) {
            liveDataMerger.value = CollectionData(it)
        }
        liveDataMerger.addSource(factory.stateLiveSource()) {
            liveDataMerger.value = StateData(it)
        }
        return liveDataMerger
    }
}