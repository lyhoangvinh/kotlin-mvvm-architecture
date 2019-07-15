package com.lyhoangvinh.simple.ui.features.test2

import android.content.Context
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentTestBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import com.lyhoangvinh.simple.ui.features.test.MainAdapter

class TestFragment : BaseViewModelRecyclerViewFragment<FragmentTestBinding, TestViewModel, MainAdapter>() {
    override fun createViewModelClass() = TestViewModel::class.java
    override fun getLayoutResource() = R.layout.fragment_test
    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        binding.vm = viewModel
    }
}