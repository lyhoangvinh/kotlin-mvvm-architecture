package com.lyhoangvinh.simple.ui.features.avg.main.collection

import android.content.Context
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.FragmentCollectionBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelPagingFragment
import com.lyhoangvinh.simple.utils.setStatusBarGradient

class CollectionFragment :
    BaseViewModelPagingFragment<FragmentCollectionBinding, CollectionViewModel, CollectionsAdapter>() {
    override fun createViewModelClass() = CollectionViewModel::class.java
    override fun getLayoutResource() = R.layout.fragment_collection
    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        setStatusBarGradient()
        binding.vm = viewModel
        binding.toolbar.imvBack.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }
}