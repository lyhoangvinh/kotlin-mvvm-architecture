package com.lyhoangvinh.simple.data.source.avg

import androidx.paging.DataSource
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.CollectionsResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.base.service.BaseRxPageKeyedDataSource
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class CollectionRxDataSource @Inject constructor(private val avgleService: AvgleService) :
    BaseRxPageKeyedDataSource<Collection, CollectionsResponseAvgle>() {

    override fun getResourceFollowable(page: Int): Flowable<Resource<BaseResponseAvgle<CollectionsResponseAvgle>>> {
        return getRepoCollections(page)
    }

    private fun getRepoCollections(page: Int): Flowable<Resource<BaseResponseAvgle<CollectionsResponseAvgle>>> {
        return createResource( avgleService.getCollections(page, 50),
            onSave = object : PlainConsumer<BaseResponseAvgle<CollectionsResponseAvgle>> {
                override fun accept(t: BaseResponseAvgle<CollectionsResponseAvgle>) {
                    if (t.success) {
                        val collections = t.response.collections
                        for (x in 0 until collections.size) {
                            collections[x].type = Constants.TYPE_ALL
                        }
//                        if (isRefresh) {
//                            execute {
//                                collectionDao.deleteType(Constants.TYPE_ALL)
//                                collectionDao.insertIgnore(collections)
//                                collectionDao.updateIgnore(collections)
//                            }
//                        } else {
//                            execute {
//                                collectionDao.insertIgnore(collections)
//                                collectionDao.updateIgnore(collections)
//                            }
//                        }
                    }
                }
            })
    }

    @Singleton
    class CollectionRxFactory @Inject constructor(private val provider: Provider<CollectionDataSource>) :
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