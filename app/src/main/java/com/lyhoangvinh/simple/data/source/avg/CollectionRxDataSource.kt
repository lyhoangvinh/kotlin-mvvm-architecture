package com.lyhoangvinh.simple.data.source.avg


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
import io.reactivex.disposables.CompositeDisposable
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
        private var compositeDisposable: CompositeDisposable? = null
        private val sourceLiveData = SafeMutableLiveData<CollectionRxDataSource>()
        private var stateLive: SafeMutableLiveData<State>? = null
        fun invalidate() {
            sourceLiveData.value?.invalidate()
        }

        fun setCompositeDisposable(compositeDisposable: CompositeDisposable) {
            this.compositeDisposable = compositeDisposable
        }

        fun setStateLiveData(stateLiveData: SafeMutableLiveData<State>) {
            this.stateLive = stateLiveData
        }

        override fun create(): DataSource<Int, Collection> {
            return CollectionRxDataSource(avgleService).apply {
                setStateLiveData(stateLive)
                setCompositeDisposable(compositeDisposable)
                sourceLiveData.postValue(this)
            }
        }
    }
}