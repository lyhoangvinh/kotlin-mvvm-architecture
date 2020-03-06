package com.lyhoangvinh.simple.ui.base.viewmodel

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.utils.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    @NonNull
    protected var mCompositeDisposable = CompositeDisposable()

    @NonNull
    val stateLiveData = SafeMutableLiveData<State>()

    private var isFirstTimeUiCreate = true

    protected lateinit var navigatorHelper: NavigatorHelper

    protected lateinit var lifecycleOwner: LifecycleOwner

    /**
     * called after fragment / activity is created with input bundle arguments
     * @param bundle argument data
     */
    @CallSuper
    fun onCreate(
        lifecycleOwner: LifecycleOwner,
        bundle: Bundle?,
        navigatorHelper: NavigatorHelper
    ) {
        this.lifecycleOwner = lifecycleOwner
        this.navigatorHelper = navigatorHelper
        if (isFirstTimeUiCreate) {
            onFirstTimeUiCreate(lifecycleOwner, bundle)
            isFirstTimeUiCreate = false
        }
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     * @param bundle
     */
    abstract fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?)

    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    open fun onDestroyView() {

    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }

    fun disposeAllExecutions() {
        mCompositeDisposable.dispose()
        mCompositeDisposable = CompositeDisposable()
        publishState(State.success())
    }

    fun publishState(state: State) {
        stateLiveData.updateValueIfNew(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({ stateLiveData.updateValueIfNew(State.success()) }, 100)
        }
    }

    protected fun <T> execute(
        showProgress: Boolean,
        resourceFollowable: Flowable<Resource<T>>,
        responseConsumer: PlainConsumer<T>?
    ) {
        mCompositeDisposable.add(resourceFollowable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe { resource ->
                if (resource != null) {
                    Log.d("source", "addRequest: resource changed: $resource")
                    if (resource.data != null && responseConsumer != null) {
                        responseConsumer.accept(resource.data)
                    }
                    if (resource.state.status == Status.LOADING && !showProgress) {
                        // do nothing if progress showing is not allowed
                    } else {
                        publishState(resource.state)
                    }
                }
            })
    }

    protected fun <T> execute(showProgress: Boolean, resourceFollowable: Flowable<Resource<T>>) {
        execute(showProgress, resourceFollowable, null)
    }

    protected fun <T> execute(showProgress: Boolean, publishState: Boolean, request: Single<T>, responseConsumer: PlainConsumer<T>?, errorConsumer: PlainConsumer<ErrorEntity>?) {
        if (showProgress && publishState) {
            publishState(State.loading())
        }
        mCompositeDisposable.add(makeRequest(request, true, newPlainConsumer {
            responseConsumer?.accept(it)
            if (publishState) {
                publishState(State.success())
            }
        }, newPlainConsumer {
            errorConsumer?.accept(it)
            if (publishState) {
                publishState(State.error(it.getMessage()))
            }
        }))
    }

    protected fun <T> execute(showProgress: Boolean, request: Single<T>, responseConsumer: PlainConsumer<T>) {
        execute(showProgress, true, request, responseConsumer, null)
    }

    protected fun <T> execute(showProgress: Boolean, request: Single<T>) {
        execute(showProgress, true, request, null, null)
    }
}