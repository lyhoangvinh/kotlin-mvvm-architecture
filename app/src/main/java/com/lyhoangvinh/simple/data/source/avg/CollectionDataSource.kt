package com.lyhoangvinh.simple.data.source.avg

import androidx.paging.DataSource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.CollectionsResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.service.BasePageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class CollectionDataSource @Inject constructor(private val avgleService: AvgleService) :
    BasePageKeyedDataSource<Collection, CollectionsResponseAvgle>() {
    override fun getRequest(page: Int): Single<BaseResponseAvgle<CollectionsResponseAvgle>> =
        avgleService.getCollections(page, 50)

    @Singleton
    class CollectionFactory @Inject constructor(private val provider: Provider<CollectionDataSource>) :
        DataSource.Factory<Int, Collection>() {

        fun stateLiveSource() = provider.get().stateLiveData

        fun setSateLiveSource(mCompositeDisposable: CompositeDisposable) {
            provider.get().compositeDisposable = mCompositeDisposable
        }

        fun clear() {
            provider.get().clear()
        }

        fun invalidate() {
            provider.get().invalidate()
        }
        override fun create(): DataSource<Int, Collection> {
            return provider.get()
        }
    }
}