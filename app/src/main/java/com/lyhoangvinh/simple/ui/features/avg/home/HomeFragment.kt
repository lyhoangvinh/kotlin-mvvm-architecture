package com.lyhoangvinh.simple.ui.features.avg.home

import android.content.Context
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentMainBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelFragment

class HomeFragment : BaseViewModelFragment<FragmentMainBinding, HomeViewModel>() {
    override fun getLayoutResource() = R.layout.fragment_main
    override fun createViewModelClass() = HomeViewModel::class.java
    override fun initialize(view: View, ctx: Context?) {
    }
}