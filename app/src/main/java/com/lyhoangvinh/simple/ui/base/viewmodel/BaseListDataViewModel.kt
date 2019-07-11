package com.lyhoangvinh.simple.ui.base.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.os.Handler
import android.os.Looper
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import com.lyhoangvinh.simple.ui.base.interfaces.LoadMoreable
import com.lyhoangvinh.simple.ui.base.interfaces.Refreshable


abstract class BaseListDataViewModel<T, A : RecyclerView.Adapter<*>> : BaseViewModel(),
    Refreshable, LoadMoreable {

    @Nullable
    lateinit var adapter: A

    var isRefreshed = false

    @Nullable
    protected lateinit var liveData: LiveData<PagedList<T>>

    @CallSuper
    open fun initAdapter(@NonNull adapter: A) {
        this.adapter = adapter
    }

    /**
     * refreshUi all paging date and re-fetch data
     */
    @CallSuper
    override fun refresh() {
        isRefreshed = true
        fetchData()
    }

    fun refresh(delay: Int) {
        Handler(Looper.myLooper()).postDelayed({ this.refresh() }, delay.toLong())
    }

    /**
     * load next page
     */
    @CallSuper
    override fun loadMore() {
        if (canLoadMore()) {
            fetchData()
        }
    }

    protected abstract fun fetchData()

}