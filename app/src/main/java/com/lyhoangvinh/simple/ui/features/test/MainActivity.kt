package com.lyhoangvinh.simple.ui.features.test

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.databinding.ActivityMainBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelRecyclerViewActivity

class MainActivity : BaseViewModelRecyclerViewActivity<ActivityMainBinding, MainViewModel, MainAdapter, Issues>() {

    override fun getLayoutResource() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }
}
