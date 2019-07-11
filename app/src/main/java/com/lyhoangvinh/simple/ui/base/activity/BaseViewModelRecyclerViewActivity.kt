package com.lyhoangvinh.simple.ui.base.activity

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.ui.base.interfaces.Scrollable
import com.lyhoangvinh.simple.ui.base.interfaces.UiRefreshable
import com.lyhoangvinh.simple.ui.base.rcvscroll.OnLoadMore
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import kotlinx.android.synthetic.main.view_recyclerview.*
import javax.inject.Inject

abstract class BaseViewModelRecyclerViewActivity<B : ViewDataBinding,
        VM : BaseListDataViewModel<T, A>,
        A : RecyclerView.Adapter<*>,
        T> : BaseViewModelActivity<B, VM>(), UiRefreshable,
    Scrollable {

    @Inject
    lateinit var adapter: A

    @Inject
    lateinit var onLoadMore: OnLoadMore

    var isRefreshing: Boolean = false

    override fun getLayoutResource() = R.layout.view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initAdapter(adapter)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        refreshLayout.setColorSchemeResources(
            R.color.material_amber_700, R.color.material_blue_700,
            R.color.material_purple_700, R.color.material_lime_700
        )
        refreshLayout.setOnRefreshListener { refresh() }
    }

    override fun setLoading(loading: Boolean) {
        super.setLoading(loading)
        if (!loading) {
            doneRefresh()
        } else {
            refreshUi()
        }
    }

    override fun onStart() {
        super.onStart()
        onLoadMore.init(recyclerView, viewModel)
    }

    override fun onStop() {
        super.onStop()
        onLoadMore.unRegisterListener(recyclerView)
    }

    override fun scrollTop(animate: Boolean) {
        if (recyclerView != null) {
            if (animate) {
                recyclerView.smoothScrollToPosition(0)
            } else {
                recyclerView.scrollToPosition(0)
            }
        }
    }

    override fun refresh() {
        if (!isRefreshing) {
            viewModel.refresh()
            onLoadMore.reset()
            isRefreshing = true
        }
    }

    override fun doneRefresh() {
        if (refreshLayout != null) {
            refreshLayout.isRefreshing = false
        }
        isRefreshing = false
    }

    override fun refreshWithUi() {
        refreshWithUi(0)
    }

    override fun refreshWithUi(delay: Int) {
        if (refreshLayout != null) {
            refreshLayout.postDelayed({
                refreshUi()
                refresh()
            }, delay.toLong())
        }
    }

    protected fun refreshUi() {
        if (refreshLayout != null) {
            refreshLayout.isRefreshing = true
        }
    }

    override fun setRefreshEnabled(enabled: Boolean) {
        refreshLayout.isEnabled = enabled
    }
}