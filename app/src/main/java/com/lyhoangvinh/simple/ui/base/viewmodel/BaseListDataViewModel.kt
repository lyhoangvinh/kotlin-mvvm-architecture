package com.lyhoangvinh.simple.ui.base.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.os.Handler
import android.os.Looper
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import com.lyhoangvinh.simple.ui.base.interfaces.PaginationListener
import com.lyhoangvinh.simple.ui.base.interfaces.Refreshable


abstract class BaseListDataViewModel<T, A : RecyclerView.Adapter<*>> : BaseViewModel(), PaginationListener,
    Refreshable {

    @Nullable
    lateinit var adapter : A

    @Nullable
    protected lateinit var liveData: LiveData<PagedList<T>>

    private var lastPage = 0

    private var currentPage = 0

    private var previousTotal = 0

    @CallSuper
    open fun initAdapter(@NonNull adapter: A) {
        this.adapter = adapter
    }

    override fun refresh() {
        onCallApi(0)
    }

    fun refresh(delay: Int) {
        Handler(Looper.myLooper()).postDelayed({ this.refresh() }, delay.toLong())
    }

    override fun onCallApi(page: Int): Boolean {
//        if (page > lastPage || lastPage == 0) {
//            return false
//        }
        currentPage = page
        callApi(lastPage, object : OnCallApiDone<T> {
            override fun onDone(last: Int) {
                lastPage = last
            }
        })
        return true
    }

    protected abstract fun callApi(page: Int, onCallApiDone: OnCallApiDone<T>)

    interface OnCallApiDone<E> {
        /**
         * Called after success response come
         * @param last last page
         */
        fun onDone(last: Int)
    }

    override fun getCurrentPage() = currentPage

    override fun setCurrentPage(page: Int) {
        this.currentPage = page
    }

    override fun getPreviousTotal() = previousTotal

    override fun setPreviousTotal(previousTotal: Int) {
        this.previousTotal = previousTotal
    }

}