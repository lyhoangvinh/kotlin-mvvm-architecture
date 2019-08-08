package com.lyhoangvinh.simple.ui.features.avg.main.video

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.data.repo.VideoRepo
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import java.net.URLEncoder
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val videoRepo: VideoRepo) : BasePagingViewModel<VideoAdapter>() {

    var title = "All"

    var query = ""

    override fun fetchData() {
        videoRepo.reSet()
        publishState(State.success(null))
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        if (bundle != null) {
            if (bundle.getParcelable<Category>(Constants.EXTRA_DATA) is Category) {
                val category: Category = bundle.getParcelable(Constants.EXTRA_DATA)
                title = category.name.toString()
//                query = URLEncoder.encode(category.CHID.toString(), "utf-8")
                query = category.CHID.toString()
            } else if (bundle.getParcelable<Collection>(Constants.EXTRA_DATA) is Collection) {
                val collection: Collection = bundle.getParcelable(Constants.EXTRA_DATA)
                title = collection.title.toString()
//                query = URLEncoder.encode(collection.keyword.toString(), "utf-8")
                query =collection.keyword.toString()
            }
        } else {
            title = "All"
            query = ""
        }
        videoRepo.liveVideo(query, stateLiveData, mCompositeDisposable).observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
        videoRepo.stateVideoSource().observe(lifecycleOwner, Observer {
            adapter.submitState(it)
        })
    }
}