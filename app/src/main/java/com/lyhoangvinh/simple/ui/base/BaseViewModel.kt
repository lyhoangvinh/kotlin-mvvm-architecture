package com.lyhoangvinh.simple.ui.base

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.os.Handler
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.text.TextUtils
import com.lyhoangvinh.simple.data.source.State
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    @NonNull
    private var mCompositeDisposable = CompositeDisposable()

    @NonNull
    var stateLiveData = SafeMutableLiveData<State>()

    private var isFirstTimeUiCreate = true

    /**
     * called after fragment / activity is created with input bundle arguments
     * @param bundle argument data
     */
    @CallSuper
    fun onCreate(bundle: Bundle?) {
        if (isFirstTimeUiCreate) {
            onFirsTimeUiCreate(bundle)
            isFirstTimeUiCreate = false
        }
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     * @param bundle
     */
    abstract fun onFirsTimeUiCreate(bundle: Bundle?)

    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    fun onDestroyView() {

    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }

    fun disposeAllExecutions() {
        mCompositeDisposable.dispose()
        mCompositeDisposable = CompositeDisposable()
        publishState(State.success(null))
    }

    fun publishState(state: State) {
        stateLiveData.setValue(state)
        if (!TextUtils.isEmpty(state.message)) {
            // if state has a message, after show it, we should reset to prevent
            // message will still be shown if fragment / activity is rotated (re-observe state live data)
            Handler().postDelayed({ stateLiveData.setValue(State.success(null)) }, 100)
        }
    }
}