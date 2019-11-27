package com.lyhoangvinh.simple.ui.features.avg.main

import com.lyhoangvinh.simple.ui.base.activity.BaseSingleFragmentActivity
import com.lyhoangvinh.simple.ui.features.avg.main.home.HomeFragment

class AvgActivity : BaseSingleFragmentActivity<HomeFragment>() {
    override fun createFragment() = HomeFragment()
}