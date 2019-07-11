package com.lyhoangvinh.simple.ui.base.rcvscroll

import android.support.v7.widget.RecyclerView
import com.lyhoangvinh.simple.ui.base.interfaces.PaginationListener
import javax.inject.Inject

class OnLoadMore @Inject constructor() : InfiniteScroll() {

    private var listener: PaginationListener? = null

    override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
        if (listener != null) {
            listener!!.setPreviousTotal(totalItemsCount)
            return listener!!.onCallApi(page + 1)
        }
        return true
    }

    fun init(recyclerView: RecyclerView, listener: PaginationListener?) {
        this.listener = listener
        if (listener != null) {
            this.initialize(listener.getCurrentPage(), listener.getPreviousTotal())
        }
        recyclerView.addOnScrollListener(this)
    }

    fun unRegisterListener(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(this)
        this.listener = null
    }
}