package com.lyhoangvinh.simple.ui.features.comic.testactivity

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityMainBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelRecyclerViewActivity

class ComicActivity : BaseViewModelRecyclerViewActivity<ActivityMainBinding, ComicViewModel, ComicAdapter>() {
    override fun getLayoutResource() = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }
}
