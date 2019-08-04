package com.lyhoangvinh.simple.ui.features.avg.splash

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivitySplashBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity
import com.lyhoangvinh.simple.utils.setStatusBarGradients

class SplashActivity : BaseViewModelActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun getLayoutResource() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarGradients()
    }
}