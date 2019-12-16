package com.lyhoangvinh.simple.ui.features.setting

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivitySettingBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity
import javax.inject.Inject

class SettingActivity : BaseViewModelActivity<ActivitySettingBinding, SettingViewModel>() {
    @Inject
    lateinit var settingAdapter: SettingAdapter
    override fun getLayoutResource() = R.layout.activity_setting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.rcvSetting.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvSetting.adapter = settingAdapter
        viewModel.initAdapter(settingAdapter)
        binding.toolbar.inflateMenu(R.menu.menu_setting)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.setting) {
                navigatorHelper.navigateSplashActivity(this)
            }
            return@setOnMenuItemClickListener false
        }
    }
}