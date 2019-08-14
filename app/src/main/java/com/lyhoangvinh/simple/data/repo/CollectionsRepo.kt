package com.lyhoangvinh.simple.data.repo

import android.util.Log
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
import com.lyhoangvinh.simple.data.source.avg.CollectionRxDataSource
import com.lyhoangvinh.simple.data.source.base.service.BaseRxPageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CollectionsRepo @Inject constructor(
    private val factory: CollectionDataSource.CollectionFactory,
    private val rxFactory: CollectionRxDataSource.CollectionRxFactory
) : BaseRepo() {

    private lateinit var liveData: LiveData<PagedList<Collection>>
    var TAG_X = "LOG_BASE_PageKeyedDataSource"

    private fun rxLiveData(): LiveData<PagedList<Collection>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(60)
            .setPrefetchDistance(60)
            .build()
        liveData = LivePagedListBuilder(rxFactory, config)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Collection>(){
                override fun onItemAtEndLoaded(itemAtEnd: Collection) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    Log.d(TAG_X, "reached end of feed")
                }
            })
            .build()
        return liveData
    }

    fun rxFetchData(): MediatorLiveData<MergedData> {
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(rxLiveData()) {
            liveDataMerger.value = CollectionData(it)
        }
        liveDataMerger.addSource(rxFactory.stateLiveData()) {
            liveDataMerger.value = StateData(it)
        }
        return liveDataMerger
    }

    fun invalidateDataSource() {
        execute {
            rxFactory.invalidate()
        }
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

    fun fetchData(): MediatorLiveData<MergedData> {
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(liveData()) {
            liveDataMerger.value = CollectionData(it)
        }
        liveDataMerger.addSource(factory.stateLiveSource()) {
            liveDataMerger.value = StateData(it)
        }
        return liveDataMerger
    }

    fun dispose(){
        rxFactory.dispose()
    }
}