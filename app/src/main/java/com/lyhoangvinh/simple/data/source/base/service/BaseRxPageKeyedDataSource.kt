package com.lyhoangvinh.simple.data.source.base.service

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.Entities
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.base.SimpleNetworkBoundSource
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * I learned a lot from this article:
 * http://huoshan2.com/recordGrowth-90377318.html
 * https://developer.android.com/topic/libraries/architecture/paging/data
 * https://medium.com/@SaurabhSandav/using-android-paging-library-with-retrofit-fa032cac15f8
 */

abstract class BaseRxPageKeyedDataSource<E, T : Entities<E>> : PageKeyedDataSource<Int, E>() {

    var TAG_X = "LOG_BASE_PageKeyedDataSource"

    private var stateLiveData: SafeMutableLiveData<State>? = null

    private var emptyLiveData: SafeMutableLiveData<DataEmpty>? = null

    private var compositeDisposable: CompositeDisposable? = null

    private var infinity = false

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

    fun setStateLiveData(stateLiveData: SafeMutableLiveData<State>?) {
        this.stateLiveData = stateLiveData
    }

    fun setEmptyLiveData(emptyLiveData: SafeMutableLiveData<DataEmpty>?) {
        this.emptyLiveData = emptyLiveData
    }

    fun setCompositeDisposable(compositeDisposable: CompositeDisposable?) {
        this.compositeDisposable = compositeDisposable
    }

    fun setUpPageKeyedDataSource(
        stateLiveData: SafeMutableLiveData<State>?,
        emptyLiveData: SafeMutableLiveData<DataEmpty>?,
        compositeDisposable: CompositeDisposable?
    ) {
        this.stateLiveData = stateLiveData
        this.emptyLiveData = emptyLiveData
        this.compositeDisposable = compositeDisposable
    }

    private fun callApi(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, E>? = null,
        loadCallback: LoadCallback<Int, E>? = null
    ) {
        publishState(State.loading(null))
        if (compositeDisposable == null) {
            return
        }
        compositeDisposable?.add(getResourceFollowable(page).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe { resource ->
                if (resource != null) {
                    Log.d("source", "addRequest: resource changed: $resource")
                    if (resource.data != null) {
                        val nextPage = page + 1
                        if (infinity) {
                            loadInitialCallback?.onResult(resource.data.response.listData(), null, nextPage)
                        } else {
                            loadInitialCallback?.onResult(resource.data.response.listData(), 0, resource.data.response.listData().size, null, nextPage)
                        }
                        loadCallback?.onResult(resource.data.response.listData(), nextPage)
                        if (loadInitialCallback != null) {
                            if (resource.data.response.listData().isNullOrEmpty()) {
                                publishDataEmpty(DataEmpty(true, ""))
                            } else {
                                publishDataEmpty(DataEmpty(false, ""))
                            }
                        }
                    }
                    if (resource.state.status == Status.LOADING) {
                        // do nothing if progress showing is not allowed
                    } else {
                        publishState(resource.state)
                    }
                }
            })
    }

    abstract fun getResourceFollowable(page: Int): Flowable<Resource<BaseResponseAvgle<T>>>

    private fun publishState(state: State) {
        if (stateLiveData == null) {
            return
        }
        stateLiveData?.postValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            //            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({
                stateLiveData?.setValue(
                    State.success(
                        null
                    )
                )
            }, 100)
        }
    }

    private fun publishDataEmpty(dataEmpty: DataEmpty) {
        if (emptyLiveData == null) return

        emptyLiveData?.setValue(dataEmpty)
    }

    /**
     * For single data
     * @param remote
     * @param onSave
     * @param <T>
     * @return
    </T> */
    fun <T> createResource(
        remote: Single<T>,
        onSave: PlainConsumer<T>?
    ): Flowable<Resource<T>> {
        return Flowable.create({
            object : SimpleNetworkBoundSource<T>(it, true) {
                override fun getRemote() = remote
                override fun saveCallResult(data: T, isRefresh: Boolean) {
                    onSave?.accept(data)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * For a list of data
     * @param isRefresh
     * @param remote
     * @param onSave
     * @param <T>
     * @return
    </T> */
    fun <T> createResource(
        isRefresh: Boolean,
        remote: Single<T>,
        onSave: OnSaveResultListener<T>
    ): Flowable<Resource<T>> {
        return Flowable.create({
            object : SimpleNetworkBoundSource<T>(it, isRefresh) {
                override fun getRemote() = remote
                override fun saveCallResult(data: T, isRefresh: Boolean) {
                    onSave.onSave(data, isRefresh)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    interface OnSaveResultListener<T> {
        fun onSave(data: T, isRefresh: Boolean)
    }
}