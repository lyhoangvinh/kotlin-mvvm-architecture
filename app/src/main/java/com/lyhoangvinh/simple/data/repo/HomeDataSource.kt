package com.lyhoangvinh.simple.data.repo

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.lyhoangvinh.simple.data.dao.CategoriesDao
import com.lyhoangvinh.simple.data.dao.CollectionDao
import com.lyhoangvinh.simple.data.dao.VideosDao
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import lyhoangvinh.com.myutil.thread.UIThreadExecutor
import javax.inject.Inject
import javax.inject.Provider

class HomeDataSource @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val collectionDao: CollectionDao,
    private val videosDao: VideosDao,
    private val uiThreadExecutor: UIThreadExecutor
) : PageKeyedDataSource<Int, ItemViewModel>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ItemViewModel>) {
//        load(page = 0, loadInitialCallback = callback)
        val list = ArrayList<ItemViewModel>()

        callback.onResult(list, null, list.size + 1)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemViewModel>) {
//        load(page = params.key, loadCallback = callback)
        val list = ArrayList<ItemViewModel>()

        val nextKey = params.key + list.size
        callback.onResult(list, nextKey)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemViewModel>) {
    }




    class Factory @Inject constructor(
        private val provider: Provider<HomeDataSource>
    ) : DataSource.Factory<Int, ItemViewModel>() {
        override fun create(): DataSource<Int, ItemViewModel> {
            return provider.get()
        }
    }
}