package com.lyhoangvinh.simple.ui.features.setting

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.SharedPrefs
import com.lyhoangvinh.simple.data.entities.OptionEntity
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.OptionSharedPreferenceLiveData
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val optionSharedPrefsLiveData: OptionSharedPreferenceLiveData
) : BaseViewModel() {

    val title = "Setting"

    var adapter: SettingAdapter? = null

    fun initAdapter(adapter: SettingAdapter) {
        this.adapter = adapter
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        optionSharedPrefsLiveData.observe(lifecycleOwner, Observer {
            adapter?.submitList(listOptions(it))
        })
    }

    fun listOptions(i: Int): List<OptionEntity> = arrayListOf<OptionEntity>()
        .apply {
            add(OptionEntity(0, false, "Comicvine"))
            add(OptionEntity(1, false, "AVG"))
            add(OptionEntity(2, false, "COMIC AVG"))
        }
        .apply {
            find { it.index == i }?.isCheck = true
        }
}