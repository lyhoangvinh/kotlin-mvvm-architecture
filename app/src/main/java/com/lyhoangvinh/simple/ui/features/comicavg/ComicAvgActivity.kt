package com.lyhoangvinh.simple.ui.features.comicavg

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityComicAvgBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity
import com.lyhoangvinh.simple.ui.widget.recycleview.RecyclerTabLayout
import javax.inject.Inject

class ComicAvgActivity : BaseViewModelActivity<ActivityComicAvgBinding, ComicAvgViewModel>() {
    override fun getLayoutResource() = R.layout.activity_comic_avg
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_setting)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.setting) {
                navigatorHelper.navigateSettingActivity()
            }
            return@setOnMenuItemClickListener false
        }
        val adapter = ComicAvgPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setUpWithViewPager2(binding.viewPager, null)
        viewModel.initAdapter(adapter, binding.tabLayout.adapter as RecyclerTabLayout.CustomTabAdapter)
        binding.viewPager.setCurrentItem(adapter.getCenterPosition(10), false)
    }
}