package com.lyhoangvinh.simple.data.source.service

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.lyhoangvinh.simple.data.entinies.ErrorEntity
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.source.State
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.PlainPagingConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.makeRequest
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Provider

//http://huoshan2.com/recordGrowth-90377318.html
/**
 * https://developer.android.com/topic/libraries/architecture/paging/data
 * https://medium.com/@SaurabhSandav/using-android-paging-library-with-retrofit-fa032cac15f8
 */

abstract class BaseItemKeyedDataSource<T> :

    PageKeyedDataSource<Int, T>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        Log.d(TAG_X, "1-loadInitial: requestedLoadSize ${params.requestedLoadSize}")
        callApi(page = 0, loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        Log.d(TAG_X, "2-loadAfter: key ${params.key}, requestedLoadSize ${params.requestedLoadSize}")
        callApi(page = params.key, loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // Do nothing, since data is loaded from our initial load itself
    }

    private var TAG_X = "LOG_BASE_PageKeyedDataSource"

    var pageNumber = 0

    lateinit var stateLiveData: SafeMutableLiveData<State>

    lateinit var compositeDisposable: CompositeDisposable


    open fun clear() {
        compositeDisposable.clear()
    }

    private fun callApi(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, T>? = null,
        loadCallback: LoadCallback<Int, T>? = null
    ) {
        publishState(State.loading(null))
        compositeDisposable.add(makeRequest(this.getRequest(), object : PlainPagingConsumer<T> {
            override fun accept(t: List<T>) {
                val nextPage = page + 1
                loadInitialCallback?.onResult(t, 0, t.size, null, nextPage)
                loadCallback?.onResult(t, nextPage)
                publishState(State.success(null))
                pageNumber = nextPage
            }
        }, object : PlainConsumer<ErrorEntity> {
            override fun accept(t: ErrorEntity) {
                publishState(State.error(t.getMessage()))
            }
        }))
    }


    abstract fun getRequest(): Single<BaseResponseComic<T>>

    fun publishState(state: State) {
        stateLiveData.setValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            //            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({
                stateLiveData.setValue(
                    State.success(
                        null
                    )
                )
            }, 100)
        }
    }

    abstract class Factory<T>(private val provider: Provider<BaseItemKeyedDataSource<T>>) :
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

    /**
     * Excute room
    </T> */

    fun execute(action: () -> Unit) {
        Completable.fromAction {
            action.invoke()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    interface OnSaveResultListener<T> {
        fun onSave(data: T, isRefresh: Boolean)
    }

}