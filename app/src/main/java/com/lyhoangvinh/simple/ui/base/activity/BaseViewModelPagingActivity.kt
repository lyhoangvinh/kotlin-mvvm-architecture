package com.lyhoangvinh.simple.ui.base.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ViewRecyclerviewBinding
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import kotlinx.android.synthetic.main.view_no_data.*
import kotlinx.android.synthetic.main.view_recyclerview.*
import kotlinx.android.synthetic.main.view_scroll_top.*
import javax.inject.Inject
import com.lyhoangvinh.simple.utils.hideKeyboard


abstract class BaseViewModelPagingActivity<B : ViewDataBinding,
        VM : BasePagingViewModel<A>,
        A : RecyclerView.Adapter<*>> : BaseViewModelActivity<B, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    private var isRefreshing: Boolean = false

    private var layoutManager: RecyclerView.LayoutManager? = null

    private var mVisibleThreshold: Int = 5

    private var scrollTopPosition = DEFAULT_SCROLL_TOP_POSITION

    override fun getLayoutResource() = R.layout.view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initAdapter(adapter)
        layoutManager = createLayoutManager()
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateScrollTop()
                // dy < 0 mean scrolling up
                if (dy < 0) return
                when (layoutManager) {
                    is LinearLayoutManager -> {
                        val visibleItemCount = layoutManager!!.childCount
                        val pastVisibleItems =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        updateScrollTop(visibleItemCount, pastVisibleItems)
                    }
                }
            }
        })
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setColorSchemeResources(
            R.color.material_amber_700,
            R.color.material_blue_700,
            R.color.material_purple_700,
            R.color.material_lime_700
        )
        scrollTop.visibility = View.GONE
        scrollTop.setOnClickListener { recyclerView.scrollToPosition(0) }

        recyclerView.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this)

    override fun setLoading(loading: Boolean) {
        if (!loading) {
            doneRefresh()
        } else {
            refreshUi()
        }
    }

    override fun onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            viewModel.refresh()
        }
    }


    fun doneRefresh() {
        updateScrollTop()
        if (refreshLayout != null) {
            refreshLayout.isRefreshing = false
        }
        isRefreshing = false
    }

    private fun refreshUi() {
        if (refreshLayout != null) {
            refreshLayout.isRefreshing = true
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


    /**
     * Show scroll top view (click on it to scroll recycler view to top)
     * if user scroll down more than [.DEFAULT_SCROLL_TOP_POSITION]
     */

    private fun getPastVisibleItems(): Int {
        return if (layoutManager is LinearLayoutManager) {
            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        } else 0
    }

    private fun updateScrollTop() {
        if (layoutManager != null) {
            val visibleItemCount = layoutManager!!.childCount
            updateScrollTop(visibleItemCount, getPastVisibleItems())
        }
    }

    /**
     * Show scroll top view (click on it to scroll recycler view to top)
     * if user scroll down more than [.DEFAULT_SCROLL_TOP_POSITION]
     */
    private fun updateScrollTop(visibleItemCount: Int, pastVisibleItems: Int) {
        if (visibleItemCount + pastVisibleItems >= scrollTopPosition) {
            scrollTop.visibility = View.VISIBLE
        } else {
            scrollTop.visibility = View.GONE
        }
    }

    companion object {
        private const val DEFAULT_SCROLL_TOP_POSITION = 10
    }
}