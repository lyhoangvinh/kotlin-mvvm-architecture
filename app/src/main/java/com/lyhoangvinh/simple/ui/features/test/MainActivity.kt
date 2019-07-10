package com.lyhoangvinh.simple.ui.features.test

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityMainBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity

class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutResource() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        val adapter = MainAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcv.adapter = adapter
        binding.vm!!.liveData().observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
