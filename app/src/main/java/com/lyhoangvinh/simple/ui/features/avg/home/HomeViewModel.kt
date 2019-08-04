package com.lyhoangvinh.simple.ui.features.avg.home

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.data.response.*
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import com.lyhoangvinh.simple.ui.features.avg.home.adapter.simple.HomeSimpleAdapter
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo) : BaseListDataViewModel<HomeSimpleAdapter>() {

    private val TAG = "HomeViewModel_TAG"

    override fun fetchData(page: Int) {}

    override fun refresh() {
        super.refresh()
        execute(
            false,
            homeRepo.getRepoHome(),
            object :
                PlainConsumer<ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>> {
                override fun accept(t: ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>) {
                }
            })
    }

    fun setLayoutParams(mWidth: Int, mHeight: Int) {
        adapter.setLayoutParams(mWidth, mHeight)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        homeRepo.liveDataHome().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}