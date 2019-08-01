package com.lyhoangvinh.simple.ui.features.avg.home

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import com.lyhoangvinh.simple.ui.features.avg.home.adapter.simple.HomeSimpleAdapter
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo) : BaseListDataViewModel<HomeSimpleAdapter>() {

    override fun fetchData(page: Int) {}

    fun setLayoutParams(mWidth: Int, mHeight: Int){
        adapter.setLayoutParams(mWidth, mHeight)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        homeRepo.fetchData().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}