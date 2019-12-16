package com.lyhoangvinh.simple.ui.features.comic.testpaging

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityPagingTestBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelPagingActivity
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicAdapter

class ComicPagingActivity : BaseViewModelPagingActivity<ActivityPagingTestBinding, ComicPagingViewModel, ComicAdapter>() {
    override fun getLayoutResource() = R.layout.activity_paging_test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.toolbar.inflateMenu(R.menu.menu_home)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.setting){
                navigatorHelper.navigateSettingActivity()
            }
            return@setOnMenuItemClickListener false
        }
    }
}
