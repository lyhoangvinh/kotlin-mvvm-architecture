package com.lyhoangvinh.simple.data.source.base.service

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.ui.base.interfaces.newPlainPagingConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.makeRequest
import com.lyhoangvinh.simple.utils.newPlainConsumer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Provider

//http://huoshan2.com/recordGrowth-90377318.html
/**
 * https://developer.android.com/topic/libraries/architecture/paging/data
 * https://medium.com/@SaurabhSandav/using-android-paging-library-with-retrofit-fa032cac15f8
 */

abstract class BasePageKeyedDataComicSource<T> : PageKeyedDataSource<Int, T>() {

    private var TAG_X = "LOG_BASE_PageKeyedDataSource"

    lateinit var stateLiveData: SafeMutableLiveData<State>

    lateinit var compositeDisposable: CompositeDisposable


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        Log.d(TAG_X, "1-loadInitial: requestedLoadSize ${params.requestedLoadSize}")
        callApi(page = 0, loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        Log.d(
            TAG_X,
            "2-loadAfter: key ${params.key}, requestedLoadSize ${params.requestedLoadSize}"
        )
        callApi(page = params.key, loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // Do nothing, since data is loaded from our initial load itself
    }

    open fun clear() {
        compositeDisposable.clear()
    }

    private fun callApi(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, T>? = null,
        loadCallback: LoadCallback<Int, T>? = null
    ) {
        publishState(State.loading())
        compositeDisposable.add(makeRequest(this.getRequest(page), newPlainPagingConsumer {
            val nextPage = page + 1
            loadInitialCallback?.onResult(it, 0, it.size, null, nextPage)
            loadCallback?.onResult(it, nextPage)
            publishState(State.success())
        }, newPlainConsumer { publishState(State.error(it.getMessage())) }))
    }

    abstract fun getRequest(page: Int): Single<BaseResponseComic<T>>

    fun publishState(state: State) {
        stateLiveData.setValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            //            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({
                stateLiveData.setValue(
                    State.success()
                )
            }, 100)
        }
    }

    abstract class Factory<T>(private val provider: Provider<BasePageKeyedDataComicSource<T>>) :
        DataSource.Factory<Int, T>() {
        private val sourceLiveData = MutableLiveData<BasePageKeyedDataComicSource<T>>()
        fun setUpProvider(
            stateLiveData: SafeMutableLiveData<State>,
            compositeDisposable: CompositeDisposable
        ) {
            this.provider.get().stateLiveData = stateLiveData
            this.provider.get().compositeDisposable = compositeDisposable
        }

        fun clear() {
            provider.get().clear()
        }

        fun invalidate() {
            sourceLiveData.value?.invalidate()
        }

        override fun create(): DataSource<Int, T> {
            sourceLiveData.postValue(provider.get())
            return provider.get()
        }
    }
}