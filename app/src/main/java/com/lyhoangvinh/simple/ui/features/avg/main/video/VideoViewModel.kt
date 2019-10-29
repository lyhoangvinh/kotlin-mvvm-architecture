package com.lyhoangvinh.simple.ui.features.avg.main.video

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.entities.avgle.VideoData
import com.lyhoangvinh.simple.data.repo.VideoRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import java.net.URLDecoder
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val videoRepo: VideoRepo) :
    BasePagingViewModel<VideoAdapter>() {

    var title = "All"

    var query = ""
    private var isFirstState = false

    override fun fetchData() {
        videoRepo.setUpRepo(URLDecoder.decode(query, "utf-8"))
        isFirstState = true
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
        videoRepo.setUpRepo(query)
        videoRepo.fetchData(mCompositeDisposable).observe(lifecycleOwner, Observer {
            when (it) {
                is VideoData -> adapter.submitList(it.videoItems)
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
    }
}