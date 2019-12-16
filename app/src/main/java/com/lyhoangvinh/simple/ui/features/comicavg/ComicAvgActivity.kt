package com.lyhoangvinh.simple.ui.features.comicavg

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityComicAvgBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity

class ComicAvgActivity : BaseViewModelActivity<ActivityComicAvgBinding, ComicAvgViewModel>() {
    override fun getLayoutResource() = R.layout.activity_comic_avg
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_home)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.setting) {
                navigatorHelper.navigateSettingActivity()
            }
            return@setOnMenuItemClickListener false
        }
        val adapter = ComicAvgPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setUpWithViewPager2(binding.viewPager, null)
        viewModel.categoriesDao.liveData().observe(this, Observer {
            binding.tabLayout?.updateData(10, arrayListOf<String>().apply {
                for (i in 0 until it.size) {
                    add(it[i].name.toString())
                }
            })
            adapter.submitData(it)
            binding.viewPager.setCurrentItem(adapter.getCenterPosition(10), false)
        })
    }
}