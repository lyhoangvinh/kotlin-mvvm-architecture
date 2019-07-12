package com.lyhoangvinh.simple.ui.base.viewmodel

import android.os.Handler
import android.os.Looper
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lyhoangvinh.simple.data.entinies.DataEmpty
import com.lyhoangvinh.simple.ui.base.interfaces.LoadMoreable
import com.lyhoangvinh.simple.ui.base.interfaces.Refreshable
import com.lyhoangvinh.simple.utils.SafeMutableLiveData


abstract class BaseListDataViewModel<A : RecyclerView.Adapter<*>> : BaseViewModel(),
    Refreshable, LoadMoreable {

    @Nullable
    lateinit var adapter: A

    var isRefreshed = false

    private var page = CURRENT_PAGE

    var dataEmptySafeMutableLiveData = SafeMutableLiveData<DataEmpty>()

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
        fetchData(0)
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
            page += 1
            fetchData(page)
        }
    }

    protected abstract fun fetchData(page: Int)

    fun hideNoDataState(isEmpty: Boolean) {
        dataEmptySafeMutableLiveData.setValue(DataEmpty(isEmpty))
    }


    companion object {
        const val CURRENT_PAGE = 0
    }
}