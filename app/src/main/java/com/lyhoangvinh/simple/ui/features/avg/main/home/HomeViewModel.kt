package com.lyhoangvinh.simple.ui.features.avg.main.home

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.data.response.*
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.simple.HomeSimpleAdapter
import com.lyhoangvinh.simple.ui.observableUi.ConnectionObservable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo, val connectionObservable: ConnectionObservable) : BaseListDataViewModel<HomeSimpleAdapter>() {

    private val TAG = "HomeViewModel_TAG"

    override fun fetchData(page: Int) {
        execute(true, homeRepo.getRepoHome(), object : PlainConsumer<ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>> {
                override fun accept(t: ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>) {
                }
            })
    }

    fun setLayoutParams(mWidth: Int, mHeight: Int, activity: Activity) {
        adapter.setLayoutParams(mWidth, mHeight, activity)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        connectionObservable.observableConnection(lifecycleOwner)
        updateData(lifecycleOwner)
    }

    private fun updateData(lifecycleOwner: LifecycleOwner) {
        homeRepo.liveDataHome().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}