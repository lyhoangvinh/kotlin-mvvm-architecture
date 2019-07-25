package com.lyhoangvinh.simple.ui.features.avg.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo) : BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        homeRepo.liveData().observe(lifecycleOwner, Observer {
            Log.d("ABC", "Log TEST: ${it.size}")
        })
    }
}