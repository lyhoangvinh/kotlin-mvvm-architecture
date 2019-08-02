package com.lyhoangvinh.simple.ui.features.avg

import com.lyhoangvinh.simple.ui.base.activity.BaseSingleFragmentActivity
import com.lyhoangvinh.simple.ui.features.avg.home.HomeFragment

class AgvActivity : BaseSingleFragmentActivity<HomeFragment>() {
    override fun createFragment() = HomeFragment()
}