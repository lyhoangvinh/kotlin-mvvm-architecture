package com.lyhoangvinh.simple.ui.features.comic.testfragment

import android.content.Context
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentTestBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelRecyclerViewFragment
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicAdapter

class ComicFragment : BaseViewModelRecyclerViewFragment<FragmentTestBinding, ComicViewModel, ComicAdapter>() {
    override fun createViewModelClass() = ComicViewModel::class.java
    override fun getLayoutResource() = R.layout.fragment_test
    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        binding.vm = viewModel
    }
}