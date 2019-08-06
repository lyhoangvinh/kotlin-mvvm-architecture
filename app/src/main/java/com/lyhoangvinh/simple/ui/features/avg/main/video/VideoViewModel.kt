package com.lyhoangvinh.simple.ui.features.avg.main.video

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.repo.VideoRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val videoRepo: VideoRepo) : BasePagingViewModel<VideoAdapter>() {

    var title = "All"

    override fun fetchData() {
        publishState(State.success(null))
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        videoRepo.liveVideo("").removeObservers(lifecycleOwner)
        videoRepo.liveVideo("").observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
        videoRepo.stateVideoSource().removeObservers(lifecycleOwner)
        videoRepo.stateVideoSource().observe(lifecycleOwner, Observer {
            adapter.submitState(it)
        })
    }

    override fun onCleared() {
        super.onCleared()
        videoRepo.clear()
    }
}