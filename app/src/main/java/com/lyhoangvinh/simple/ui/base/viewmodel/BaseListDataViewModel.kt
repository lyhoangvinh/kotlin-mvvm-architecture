package com.lyhoangvinh.simple.ui.base.viewmodel

import android.os.Handler
import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.lyhoangvinh.simple.ui.base.interfaces.LoadMoreable
import com.lyhoangvinh.simple.ui.base.interfaces.Refreshable


abstract class BaseListDataViewModel<A : RecyclerView.Adapter<*>> : BaseViewModel(),
    Refreshable, LoadMoreable {

    @Nullable
    lateinit var adapter: A

    var isRefreshed = false

    var canLoadMore = false

    var currentPage = CURRENT_PAGE

    @CallSuper
    open fun initAdapter(@NonNull adapter: A) {
        this.adapter = adapter
    }

    override fun canLoadMore() = canLoadMore

    /**
     * refreshUi all paging date and re-fetch data
     */
    @CallSuper
    override fun refresh() {
        isRefreshed = true
        fetchData(0)
    }

    fun refresh(delay: Int) {
        Handler().postDelayed({ this.refresh() }, delay.toLong())
    }

    /**
     * load next currentPage
     */
    @CallSuper
    override fun loadMore() {
        if (canLoadMore()) {
            currentPage += 1
            fetchData(currentPage)
        }
    }

    protected abstract fun fetchData(page: Int)

    /**
     *  update empty view
     */
//    fun hideNoDataState(isEmpty: Boolean) {
//        dataEmptySafeMutableLiveData.setValue(DataEmpty(isEmpty))
//    }

    companion object {
        const val CURRENT_PAGE = 0
    }
}