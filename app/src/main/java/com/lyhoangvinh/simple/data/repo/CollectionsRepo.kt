package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.entities.avgle.CollectionData
import com.lyhoangvinh.simple.data.entities.avgle.MergedData
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.source.avg.CollectionDataSource
import com.lyhoangvinh.simple.data.source.avg.CollectionRxDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class CollectionsRepo @Inject constructor(
    private val factory: CollectionDataSource.CollectionFactory,
    private val rxFactory: CollectionRxDataSource.CollectionRxFactory
) : BaseRepo() {

    fun rxFetchData(compositeDisposable: CompositeDisposable): MediatorLiveData<MergedData> {
        val stateLiveData = SafeMutableLiveData<State>()
        return MediatorLiveData<MergedData>().apply {
            addSource(
                LivePagedListBuilder(rxFactory.apply {
                    setCompositeDisposable(compositeDisposable)
                    setStateLiveData(stateLiveData)
                }, PagedList.Config.Builder().apply {
                    setPageSize(50)
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(50)
//            .setPrefetchDistance(50)
                }.build())
                    .setBoundaryCallback(object : PagedList.BoundaryCallback<Collection>() {
                        override fun onItemAtEndLoaded(itemAtEnd: Collection) {
                            super.onItemAtEndLoaded(itemAtEnd)
                            Timer("reached end of feed")
                        }
                    })
                    .build()
            ) {
                value = CollectionData(it)
            }
            addSource(stateLiveData) {
                value = StateData(it)
            }
        }
    }

    fun invalidateDataSource() {
        execute {
            rxFactory.invalidate()
        }
    }

    private fun liveData(): LiveData<PagedList<Collection>> {
        return LivePagedListBuilder(factory, PagedList.Config.Builder().apply {
            setPageSize(50)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(100)
                .setPrefetchDistance(50)
        }
            .build()).build()
    }

    fun fetchData(): MediatorLiveData<MergedData> {
        return MediatorLiveData<MergedData>().apply {
            addSource(liveData()) {
                value = CollectionData(it)
            }
            addSource(factory.stateLiveSource()) {
                value = StateData(it)
            }
        }
    }
}