package com.lyhoangvinh.simple.ui.features.comicavg.portal

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentPortalBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelFragment
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelPagingFragment
import com.lyhoangvinh.simple.ui.widget.recycleview.VerticalSpaceItemDecoration
import com.lyhoangvinh.simple.utils.SingletonHolder
import com.lyhoangvinh.simple.utils.calculateNoOfColumnsShow
import retrofit2.Retrofit

class PortalFragment :
    BaseViewModelPagingFragment<FragmentPortalBinding, PortalViewModel, PortalAdapter>() {
    override fun getLayoutResource() = R.layout.fragment_portal
    override fun createViewModelClass() = PortalViewModel::class.java
    override fun initialize(view: View, ctx: Context?) {

    }

    override fun createItemDecoration() =
        VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.padding_4dp))

    override fun createLayoutManager() =
        StaggeredGridLayoutManager(calculateNoOfColumnsShow(), StaggeredGridLayoutManager.VERTICAL)

    companion object {
        fun newInstance(keyword: String): PortalFragment {
            return PortalFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.PORTAL, keyword)
                }
            }
        }
    }
}