package com.lyhoangvinh.simple.ui.features.test

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.source.State
import com.lyhoangvinh.simple.databinding.ActivityMainBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity

class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutResource() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    override fun handleState(state: State?) {
        super.handleState(state)
        binding.tvText.text = viewModel.text
    }
}
