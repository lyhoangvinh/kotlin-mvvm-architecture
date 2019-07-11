package com.lyhoangvinh.simple.ui.base.rcvscroll

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class InfiniteScroll : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 3
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var adapter: RecyclerView.Adapter<*>

    private var newlyAdded = true

    private fun initLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        this.layoutManager = layoutManager
        if (layoutManager is GridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (newlyAdded) {
            newlyAdded = false
            return
        }
        onScrolled(dy > 0)
        if (layoutManager == null) {
            initLayoutManager(recyclerView.layoutManager)
        }
        if (adapter == null) {
            if (recyclerView.adapter is RecyclerView.Adapter<*>) {
                adapter = recyclerView.adapter as RecyclerView.Adapter<*>
            }
        }
//        if (adapter != null && adapter!!.isProgressAdded()) return

        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager!!.itemCount
        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // if current data is load from room, don't perform load more event
        // if current data is load from room, don't perform load more event
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            val isCallingApi = onLoadMore(currentPage, totalItemCount)
            loading = true
            if (adapter != null && isCallingApi) {
//                adapter!!.addProgress()
            }
        }
    }

    fun reset() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    fun initialize(page: Int, previousTotal: Int) {
        this.currentPage = page
        this.previousTotalItemCount = previousTotal
        this.loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int): Boolean

    fun onScrolled(isUp: Boolean) {}

}