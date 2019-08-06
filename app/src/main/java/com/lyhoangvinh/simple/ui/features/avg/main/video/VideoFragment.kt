package com.lyhoangvinh.simple.ui.features.avg.main.video

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.databinding.FragmentVideoBinding
import com.lyhoangvinh.simple.ui.base.fragment.BaseViewModelPagingFragment
import com.lyhoangvinh.simple.utils.setStatusBarGradient

class VideoFragment : BaseViewModelPagingFragment<FragmentVideoBinding, VideoViewModel, VideoAdapter>() {
    override fun createViewModelClass() = VideoViewModel::class.java
    override fun getLayoutResource() = R.layout.fragment_video
    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
        setStatusBarGradient()
        binding.vm = viewModel
        binding.toolbar.imvBack.setOnClickListener { onBackPressed() }
    }

    override fun handleState(state: State?) {
        doneRefresh()
    }

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }
}