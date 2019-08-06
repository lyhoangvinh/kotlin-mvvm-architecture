package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.source.avg.VideoDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VideoRepo @Inject constructor(private val videoFactory: VideoDataSource.VideoFactory) {

    fun liveVideo(
        chId: String
    ): LiveData<PagedList<Video>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(50)
            .setPrefetchDistance(40)
            .build()
        videoFactory.setChId(chId)
        return LivePagedListBuilder(videoFactory, config).build()
    }

    fun stateVideoSource() = videoFactory.stateLiveSource()

    fun clear() {
        videoFactory.clear()
    }

    fun invalidate() {
        videoFactory.invalidate()
    }
}