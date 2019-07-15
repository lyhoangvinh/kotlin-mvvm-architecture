package com.lyhoangvinh.simple.ui.features.test2

import com.lyhoangvinh.simple.ui.base.activity.BaseSingleFragmentActivity


class TestActivity : BaseSingleFragmentActivity<TestFragment>() {
    override fun createFragment() = TestFragment()
}