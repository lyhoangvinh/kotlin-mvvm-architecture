package com.lyhoangvinh.simple.data.source.base.service

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.lyhoangvinh.simple.data.entinies.ErrorEntity
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.entinies.State
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.PlainPagingConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.makeRequest
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Provider

//http://huoshan2.com/recordGrowth-90377318.html
/**
 * https://developer.android.com/topic/libraries/architecture/paging/data
 * https://medium.com/@SaurabhSandav/using-android-paging-library-with-retrofit-fa032cac15f8
 */

abstract class BaseItemKeyedDataComicSource<T> :
    ItemKeyedDataSource<Int, T>() {

    private var TAG_X = "LOG_BASE_ItemKeyedDataComicSource"

    var pageNumber = 0

    lateinit var stateLiveData: SafeMutableLiveData<State>

    lateinit var compositeDisposable: CompositeDisposable

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<T>) {
        Log.d(TAG_X,"1-loadInitial: requestedInitialKey ${params.requestedInitialKey},requestedLoadSize ${params.requestedLoadSize}")
        callApi(loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<T>) {
        Log.d(TAG_X,"2-loadAfter: key ${params.key}, requestedLoadSize ${params.requestedLoadSize}")
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
        compositeDisposable.add(makeRequest(this.getRequest(), object : PlainPagingConsumer<T> {
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
        }))
    }

    override fun getKey(item: T) = pageNumber

    abstract fun getRequest(): Single<BaseResponseComic<T>>

    fun publishState(state: State) {
        stateLiveData.setValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            //            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({ stateLiveData.setValue(
                State.success(
                    null
                )
            ) }, 100)
        }
    }

    abstract class Factory<T>(private val provider: Provider<BaseItemKeyedDataComicSource<T>>) :
        DataSource.Factory<Int, T>() {

        fun setUpProvider(stateLiveData: SafeMutableLiveData<State>, compositeDisposable: CompositeDisposable) {
            this.provider.get().stateLiveData = stateLiveData
            this.provider.get().compositeDisposable = compositeDisposable
        }

        fun clear() {
            provider.get().clear()
        }

        fun invalidate() {
            provider.get().invalidate()
        }
        override fun create(): DataSource<Int, T> {
            return provider.get()
        }
    }
}