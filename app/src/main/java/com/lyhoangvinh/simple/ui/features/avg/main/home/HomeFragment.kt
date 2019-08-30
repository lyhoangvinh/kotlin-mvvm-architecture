package com.lyhoangvinh.simple.ui.features.avg.main.home

import android.content.Context
import android.util.Log
import android.view.View
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.databinding.FragmentHomeBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.simple.HomeSimpleAdapter
import com.lyhoangvinh.simple.utils.removeStatusBar
import com.lyhoangvinh.simple.utils.setStatusBarColor

class HomeFragment : BaseViewModelRecyclerViewFragment<FragmentHomeBinding, HomeViewModel, HomeSimpleAdapter>() {
    override fun getLayoutResource() = R.layout.fragment_home
    override fun createViewModelClass() = HomeViewModel::class.java
    override fun shouldShowScrollTop(): Boolean = false
    override fun initialize(view: View, ctx: Context?) {
        setStatusBarColor(R.color.colorWhite)
        super.initialize(view, ctx)
        binding.vm = viewModel
        val mWidth =
            (MyApplication.getInstance().getDeviceWidth() - 2 * resources.getDimensionPixelSize(R.dimen.padding_16dp)) / 4 * 3 - resources.getDimensionPixelSize(
                R.dimen.padding_16dp
            ) / 2
        val mHeight = mWidth * 5 / 7
        viewModel.setLayoutParams(mWidth, mHeight, activity!!)
    }
}