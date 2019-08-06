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
        chId: String, stateLiveData: SafeMutableLiveData<State>,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<Video>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(50)
            .setPrefetchDistance(40)
            .build()
        videoFactory.setChId(chId)
        videoFactory.setUpProvider(stateLiveData, compositeDisposable)
        return LivePagedListBuilder(videoFactory, config).build()
    }

    fun clear() {
        videoFactory.clear()
    }

    fun invalidate(){
        videoFactory.invalidate()
    }
}