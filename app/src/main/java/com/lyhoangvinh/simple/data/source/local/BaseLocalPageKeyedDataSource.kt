package com.lyhoangvinh.simple.data.source.local

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
import com.lyhoangvinh.simple.utils.ConnectionLiveData
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.makeRequest
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Provider

//http://huoshan2.com/recordGrowth-90377318.html
/**
 * https://developer.android.com/topic/libraries/architecture/paging/data
 * https://medium.com/@SaurabhSandav/using-android-paging-library-with-retrofit-fa032cac15f8
 */

abstract class BaseLocalPageKeyedDataSource<T>(connectionLiveData: ConnectionLiveData) :
    PageKeyedDataSource<Int, T>() {

    private var TAG_X = "LOG_BaseLocalPageKeyedDataSource"

    lateinit var stateLiveData: SafeMutableLiveData<State>

    lateinit var compositeDisposable: CompositeDisposable

    private var isErrorConnection = false

    private var retryCompletable: Completable? = null

    private lateinit var loadInitialParams: LoadInitialParams<Int>

    private lateinit var loadInitialCallback: LoadInitialCallback<Int, T>

    private lateinit var params: LoadParams<Int>

    private lateinit var callback: LoadCallback<Int, T>

    private var currentPage = 0

    init {
        connectionLiveData.observeForever {
            if (it!!.isConnected && isErrorConnection) {
                if (currentPage == 0) {
                    loadInitial(loadInitialParams, loadInitialCallback)
                } else {
                    loadAfter(params, callback)
                }
            }
        }
    }

    open fun clear() {
        compositeDisposable.clear()
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        this.loadInitialParams = params
        this.loadInitialCallback = callback
        Log.d(TAG_X, "1-loadInitial: requestedLoadSize ${params.requestedLoadSize}")
        callApi(page = 0, loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        this.params = params
        this.callback = callback
        Log.d(TAG_X, "2-loadAfter: key ${params.key}, requestedLoadSize ${params.requestedLoadSize}")
        callApi(page = params.key, loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // Do nothing, since data is loaded from our initial load itself
        Log.d(TAG_X, "3-loadBefore:key ${params.key}, requestedLoadSize ${params.requestedLoadSize}")

    }

    private fun callApi(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, T>? = null,
        loadCallback: LoadCallback<Int, T>? = null
    ) {
        currentPage = page
        publishState(State.loading(null))
        compositeDisposable.add(makeRequest(this.getRequest(page), object : PlainPagingConsumer<T> {
            override fun accept(t: List<T>) {
                val nextPage = page + 1
                publishState(State.success(null))
                execute {
                    saveResultListener(isRefresh = page == 0, data = t)
                    loadInitialCallback?.onResult(getResult(), 0, getResult().size, null, nextPage)
                }
                loadCallback?.onResult(getResult(), nextPage)
                isErrorConnection = false
            }
        }, object : PlainConsumer<ErrorEntity> {
            override fun accept(t: ErrorEntity) {
                publishState(State.error(t.getMessage()))
                Log.d(TAG_X, "Network error : ${t.getMessage()}")
                isErrorConnection = true
            }
        }))
    }

    abstract fun getResult(): List<T>

    abstract fun saveResultListener(isRefresh: Boolean, data: List<T>)

    abstract fun getRequest(page: Int): Single<BaseResponseComic<T>>

    fun publishState(state: State) {
        stateLiveData.setValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            //            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({
                stateLiveData.setValue(State.success(null))
            }, 100)
        }
    }

    abstract class Factory<T>(private val provider: Provider<BaseLocalPageKeyedDataSource<T>>) :
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

        fun reset() {
            provider.get().reset()
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

    fun reset() {
        if (!isErrorConnection){
//            invalidate()
            loadAfter(params, callback)
        }
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}