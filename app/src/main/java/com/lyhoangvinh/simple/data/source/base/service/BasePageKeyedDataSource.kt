package com.lyhoangvinh.simple.data.source.base.service

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.lyhoangvinh.simple.data.entities.Entities
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.ui.base.interfaces.newPlainEntitiesPagingConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.makeRequestAvg
import com.lyhoangvinh.simple.utils.newPlainConsumer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Provider

/**
 * I learned a lot from this article:
 * http://huoshan2.com/recordGrowth-90377318.html
 * https://developer.android.com/topic/libraries/architecture/paging/data
 * https://medium.com/@SaurabhSandav/using-android-paging-library-with-retrofit-fa032cac15f8
 */

abstract class BasePageKeyedDataSource<E, T : Entities<E>> : PageKeyedDataSource<Int, E>() {

    private var TAG_X = "LOG_BASE_PageKeyedDataSource"

    val stateLiveData = SafeMutableLiveData<State>()

    lateinit var compositeDisposable: CompositeDisposable

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, E>
    ) {
        Log.d(TAG_X, "1-loadInitial: requestedLoadSize ${params.requestedLoadSize}")
        callApi(page = 0, loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, E>) {
        Log.d(
            TAG_X,
            "2-loadAfter: key ${params.key}, requestedLoadSize ${params.requestedLoadSize}"
        )
        callApi(page = params.key, loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, E>) {
        // Do nothing, since data is loaded from our initial load itself
    }

    open fun clear() {
        compositeDisposable.clear()
    }

    private fun callApi(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, E>? = null,
        loadCallback: LoadCallback<Int, E>? = null
    ) {
        publishState(State.loading())
        compositeDisposable.add(
            makeRequestAvg(
                this.getRequest(page),
                newPlainEntitiesPagingConsumer {
                    val nextPage = page + 1
                    loadInitialCallback?.onResult(it, 0, it.size, null, nextPage)
                    loadCallback?.onResult(it, nextPage)
                    publishState(State.success())
                },
                newPlainConsumer { publishState(State.error(it.getMessage())) })
        )
    }

    abstract fun getRequest(page: Int): Single<BaseResponseAvgle<T>>

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

    abstract class Factory<E, T : Entities<E>>(private val provider: Provider<BasePageKeyedDataSource<E, T>>) :
        DataSource.Factory<Int, E>() {

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

        override fun create(): DataSource<Int, E> {
            return provider.get()
        }
    }
}