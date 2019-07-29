package com.lyhoangvinh.simple.ui.features.comic.testpaging

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityPagingTestBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelRecyclerViewActivity
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicAdapter

class ComicPagingActivity : BaseViewModelRecyclerViewActivity<ActivityPagingTestBinding, ComicPagingViewModel, ComicAdapter>() {
    override fun getLayoutResource() = R.layout.activity_paging_test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }
}
