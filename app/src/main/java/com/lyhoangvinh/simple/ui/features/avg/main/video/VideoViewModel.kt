package com.lyhoangvinh.simple.ui.features.avg.main.video

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.repo.VideoRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val videoRepo: VideoRepo) : BasePagingViewModel<VideoAdapter>() {

    var title = "All"

    var query = ""

    override fun fetchData() {
        videoRepo.reSet()
        videoRepo.setUpRepo(URLDecoder.decode(query, "utf-8"))
        publishState(State.success(null))
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        if (bundle != null) {
            if (bundle.getParcelable<Category>(Constants.EXTRA_DATA) is Category) {
                val category: Category = bundle.getParcelable(Constants.EXTRA_DATA)!!
                title = category.name!!
//                query = URLEncoder.encode(category.CHID.toString(), "utf-8")
                query = category.CHID!!
            } else if (bundle.getParcelable<Collection>(Constants.EXTRA_DATA) is Collection) {
                val collection: Collection = bundle.getParcelable(Constants.EXTRA_DATA)!!
                title = collection.title!!
//                query = URLEncoder.encode(collection.keyword.toString(), "utf-8")
                query = collection.keyword!!
            }
        } else {
            title = "All"
            query = ""
        }

        videoRepo.setUpRepo(URLDecoder.decode(query, "utf-8"))
        videoRepo.fetchData().observe(lifecycleOwner, Observer {
            when (it) {
                is StateData -> adapter.submitState(it.state)
                is VideoData -> adapter.submitList(it.videoItems)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        videoRepo.dispose()
    }
}