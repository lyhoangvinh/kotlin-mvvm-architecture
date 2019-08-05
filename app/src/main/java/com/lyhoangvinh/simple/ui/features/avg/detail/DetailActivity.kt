package com.lyhoangvinh.simple.ui.features.avg.detail

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.State
import com.lyhoangvinh.simple.databinding.ActivityDetailBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity

class DetailActivity : BaseViewModelActivity<ActivityDetailBinding, DetailViewModel>() {
    override fun getLayoutResource() = R.layout.activity_detail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.toolbar.imvBack.setOnClickListener { onBackPressed() }
    }

    override fun handleState(state: State?) {
        if (state != null) {
            binding.state = state
        }
    }
}