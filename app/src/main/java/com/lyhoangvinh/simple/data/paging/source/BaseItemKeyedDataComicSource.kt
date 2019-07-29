package com.lyhoangvinh.simple.data.paging.source

import android.os.Handler
import android.text.TextUtils
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.lyhoangvinh.simple.data.entinies.ErrorEntity
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.PlainPagingConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.makeRequest
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Provider

//http://huoshan2.com/recordGrowth-90377318.html

abstract class BaseItemKeyedDataComicSource<T> :
    ItemKeyedDataSource<Int, T>() {

    var pageNumber = 0

    lateinit var stateLiveData: SafeMutableLiveData<State>

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<T>) {
        callApi(loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<T>) {
        callApi(loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<T>) {
        // Do nothing, since data is loaded from our initial load itself
    }

    open fun clear() {
        compositeDisposable.clear()
    }

    private fun callApi(loadInitialCallback: LoadInitialCallback<T>? = null, loadCallback: LoadCallback<T>? = null) {
        publishState(State.loading(null))
        val disposable = makeRequest(this.getRequest(), object : PlainPagingConsumer<T> {
            override fun accept(t: List<T>) {
                loadInitialCallback?.onResult(t)
                loadCallback?.onResult(t)
                publishState(State.success(null))
                pageNumber++
            }
        }, object : PlainConsumer<ErrorEntity> {
            override fun accept(t: ErrorEntity) {
                publishState(State.error(t.getMessage()))
            }
        })
        compositeDisposable.add(disposable)
    }

    override fun getKey(item: T) = pageNumber

    abstract fun getRequest(): Single<BaseResponseComic<T>>

    fun publishState(state: State) {
        stateLiveData.setValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            //            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({ stateLiveData.setValue(State.success(null)) }, 100)
        }
    }

    abstract class Factory<T>(private val provider: Provider<BaseItemKeyedDataComicSource<T>>) :
        DataSource.Factory<Int, T>() {

        override fun create(): DataSource<Int, T> {
            return provider.get()
        }
    }
}