package com.lyhoangvinh.simple.ui.features.comicavg.portal

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.entities.avgle.VideoData
import com.lyhoangvinh.simple.data.repo.VideoRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import java.net.URLDecoder
import javax.inject.Inject

class PortalViewModel @Inject constructor(private val videoRepo: VideoRepo) :
    BasePagingViewModel<PortalAdapter>() {
    private var keyword = ""
    private var isFirstState = false
    override fun fetchData() {
        videoRepo.setUpRepo(keyword)
        isFirstState = true
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        if (bundle != null) {
            keyword = bundle.getString(Constants.PORTAL).toString()
        }
        videoRepo.fetchData(mCompositeDisposable).observe(lifecycleOwner, Observer {
            when (it) {
                is VideoData -> {
                    adapter.submitList(it.videoItems)
                }
                is StateData -> {
                    if (isFirstState) {
                        adapter.submitState(it.state)
                    } else {
                        isFirstState = true
                    }
                    publishState(it.state)
                }
            }
            if (isFirstState) {
                isFirstState = false
                adapter.submitState(State(Status.SUCCESS, null))
            }
        })
        videoRepo.setUpRepo(keyword)
    }
}