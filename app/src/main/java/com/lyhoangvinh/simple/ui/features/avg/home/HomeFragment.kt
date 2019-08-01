package com.lyhoangvinh.simple.ui.features.avg.home

import android.content.Context
import android.view.View
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentMainBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import com.lyhoangvinh.simple.ui.features.avg.home.adapter.simple.HomeSimpleAdapter

class HomeFragment : BaseViewModelRecyclerViewFragment<FragmentMainBinding, HomeViewModel, HomeSimpleAdapter>() {
    override fun getLayoutResource() = R.layout.fragment_main
    override fun createViewModelClass() = HomeViewModel::class.java
    override fun initialize(view: View, ctx: Context?) {
        val mWidth =
            (MyApplication.getInstance().getDeviceWidth() - 2 * resources.getDimensionPixelSize(R.dimen.padding_16dp)) / 4 * 3 - resources.getDimensionPixelSize(
                R.dimen.padding_16dp
            ) / 2
        val mHeight = mWidth * 5 / 7
        viewModel.setLayoutParams(mWidth, mHeight)
        binding.vm = viewModel
    }
}