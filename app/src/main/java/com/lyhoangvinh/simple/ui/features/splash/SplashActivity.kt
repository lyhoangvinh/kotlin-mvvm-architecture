package com.lyhoangvinh.simple.ui.features.splash

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.databinding.ActivitySplashBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity
import com.lyhoangvinh.simple.utils.setStatusBarGradients

class SplashActivity : BaseViewModelActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun getLayoutResource() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarGradients()
        binding.vm = viewModel
    }

    override fun handleState(state: State?) {
        if (state != null) {
            binding.state = state
        }
    }
}