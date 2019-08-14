package com.lyhoangvinh.simple.data.source.avg

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.CollectionsResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.base.service.BaseRxPageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionRxDataSource @Inject constructor(private val avgleService: AvgleService) :
    BaseRxPageKeyedDataSource<Collection, CollectionsResponseAvgle>() {

    override fun getResourceFollowable(page: Int): Flowable<Resource<BaseResponseAvgle<CollectionsResponseAvgle>>> {
        return getRepoCollections(page)
    }

    private fun getRepoCollections(page: Int): Flowable<Resource<BaseResponseAvgle<CollectionsResponseAvgle>>> {
        return createResource(avgleService.getCollections(page, 50), null)
    }

    @Singleton
    class CollectionRxFactory @Inject constructor(
        private val avgleService: AvgleService
    ) :
        DataSource.Factory<Int, Collection>() {

        private var isCurrentSource = false
        private val sourceLiveData = SafeMutableLiveData<CollectionRxDataSource>()
        private lateinit var newSource: CollectionRxDataSource

        fun invalidate() {
            sourceLiveData.value?.invalidate()
        }

        fun stateLiveData(): LiveData<State> {
            if (!isCurrentSource) {
                newSource = CollectionRxDataSource(avgleService)
                isCurrentSource = true
            }
            return if (sourceLiveData.value == null) {
                newSource.getStateLiveData()
            } else {
                sourceLiveData.value?.getStateLiveData()!!
            }
        }

        fun dispose() {
            sourceLiveData.value?.dispose()
        }

        override fun create(): DataSource<Int, Collection> {
            if (isCurrentSource) {
                newSource = CollectionRxDataSource(avgleService)
            }
            sourceLiveData.postValue(newSource)
            return newSource
        }
    }
}