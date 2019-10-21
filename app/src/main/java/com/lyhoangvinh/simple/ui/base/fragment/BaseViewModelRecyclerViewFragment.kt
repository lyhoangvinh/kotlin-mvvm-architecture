package com.lyhoangvinh.simple.ui.base.fragment

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.ui.base.interfaces.LoadMoreable
import com.lyhoangvinh.simple.ui.base.interfaces.UiRefreshable
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import kotlinx.android.synthetic.main.view_no_data.*
import kotlinx.android.synthetic.main.view_recyclerview.*
import kotlinx.android.synthetic.main.view_scroll_top.*
import javax.inject.Inject
import android.view.animation.AnimationUtils.loadLayoutAnimation
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewModelRecyclerViewFragment<B : ViewDataBinding,
        VM : BaseListDataViewModel<A>,
        A : RecyclerView.Adapter<*>> : BaseViewModelFragment<B, VM>(),
    UiRefreshable,
    LoadMoreable,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    private var isRefreshing: Boolean = false

    private var layoutManager: RecyclerView.LayoutManager? = null

    private var mVisibleThreshold: Int = 5

    private var scrollTopPosition = DEFAULT_SCROLL_TOP_POSITION

    override fun getLayoutResource() = R.layout.view_recyclerview

    override fun initialize(view: View, ctx: Context?) {
        viewModel.initAdapter(adapter)
        layoutManager = createLayoutManager()
        recyclerView.layoutManager = layoutManager
//        recyclerView.itemAnimator = DefaultItemAnimator()
        runLayoutAnimation(recyclerView)
        recyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setColorSchemeResources(
            R.color.material_amber_700, R.color.material_blue_700,
            R.color.material_purple_700, R.color.material_lime_700
        )
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateScrollTop()
                // dy < 0 mean scrolling up
                if (dy < 0) return
                val totalItemCount = layoutManager!!.itemCount
                var lastVisibleItemPosition = 0
                when (layoutManager) {
                    is StaggeredGridLayoutManager -> {
                        val lastVisibleItemPositions =
                            (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(
                                null
                            )
                        lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
                        mVisibleThreshold *= (layoutManager as StaggeredGridLayoutManager).spanCount
                    }
                    is GridLayoutManager -> {
                        mVisibleThreshold *= (layoutManager as GridLayoutManager).spanCount
                        lastVisibleItemPosition =
                            (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    }
                    is LinearLayoutManager -> {
                        lastVisibleItemPosition =
                            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        val visibleItemCount = layoutManager!!.childCount
                        val pastVisibleItems = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        updateScrollTop(visibleItemCount, pastVisibleItems)
                    }
                }
                if (lastVisibleItemPosition + mVisibleThreshold > totalItemCount) {
                    loadMore()
                }
            }
        })
        scrollTop.visibility = View.GONE
        scrollTop.setOnClickListener { recyclerView.scrollToPosition(0) }
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }

    /**
     * Load more item after scrolling down to bottom of current list
     */
    override fun loadMore() {
        if ((viewModel as LoadMoreable).canLoadMore()) {
            (viewModel as LoadMoreable).loadMore()
            isRefreshing = true
        }
    }

    override fun canLoadMore(): Boolean = false

    open fun createLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(activity)

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
            refresh()
        }
    }

    override fun refresh() {
        viewModel.refresh()
    }

    override fun doneRefresh() {
        updateScrollTop()
        if (refreshLayout != null) {
            refreshLayout.isRefreshing = false
        }
        isRefreshing = false
    }

    override fun refreshWithUi() {
        refreshWithUi(100L)
    }

    override fun refreshWithUi(delay: Long) {
        if (refreshLayout != null) {
            refreshLayout.postDelayed({
                refreshUi()
                refresh()
            }, delay)
        }
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
        if (layoutManager != null && shouldShowScrollTop()) {
            val visibleItemCount = layoutManager!!.childCount
            updateScrollTop(visibleItemCount, getPastVisibleItems())
        }
    }

    /**
     * Show scroll top view (click on it to scroll recycler view to top)
     * if user scroll down more than [.DEFAULT_SCROLL_TOP_POSITION]
     */
    private fun updateScrollTop(visibleItemCount: Int, pastVisibleItems: Int) {
        if (shouldShowScrollTop()) {
            if (visibleItemCount + pastVisibleItems >= scrollTopPosition) {
                scrollTop.visibility = View.VISIBLE
            } else {
                scrollTop.visibility = View.GONE
            }
        }
    }

    open fun shouldShowScrollTop(): Boolean {
        return true
    }

    companion object {
        private const val DEFAULT_SCROLL_TOP_POSITION = 10
    }
}