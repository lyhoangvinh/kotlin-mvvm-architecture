package com.lyhoangvinh.simple.ui.features.comic.testfragment

import com.lyhoangvinh.simple.ui.base.activity.BaseSingleFragmentActivity


class ComicSingleActivity : BaseSingleFragmentActivity<ComicFragment>() {
    override fun createFragment() = ComicFragment()
}