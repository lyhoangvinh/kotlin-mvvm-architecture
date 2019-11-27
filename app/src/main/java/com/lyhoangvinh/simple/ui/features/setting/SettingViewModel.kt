package com.lyhoangvinh.simple.ui.features.setting

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.SharedPrefs
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class SettingViewModel @Inject constructor(private val sharedPrefs: SharedPrefs) : BaseViewModel(){

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        val options = sharedPrefs[Constants.OPTION, Int::class.java]
    }
}