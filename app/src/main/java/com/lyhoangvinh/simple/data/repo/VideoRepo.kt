package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.source.avg.VideoDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.genericCastOrNull
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lyhoangvinh.com.myutil.thread.BackgroundThreadExecutor
import javax.inject.Inject

class VideoRepo @Inject constructor(private val videoFactory: VideoDataSource.VideoFactory) :
    BaseRepo() {

    private lateinit var live: LiveData<PagedList<Video>>

    private var chId: String = ""

    fun setUpRepo(chId: String) {
        this.chId = chId
        execute {
            videoFactory.setChId(chId)
            videoFactory.invalidate()
        }
    }

    private fun liveVideo(): LiveData<PagedList<Video>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(100)
            .setPrefetchDistance(50)
            .build()
        live = LivePagedListBuilder(videoFactory, config).build()
        return live
    }

    fun fetchData(): MediatorLiveData<MergedData> {
        val liveDataMerger = MediatorLiveData<MergedData>()
        liveDataMerger.addSource(liveVideo()) {
            liveDataMerger.value = VideoData(it)
        }
        liveDataMerger.addSource(videoFactory.stateLiveSource()) {
            liveDataMerger.value = StateData(it)
        }
        return liveDataMerger
    }

    fun stateLiveSource() = videoFactory.stateLiveSource()

    fun reSet() {
        execute {
            videoFactory.invalidate()
        }
    }

    fun dispose() = videoFactory.dispose()
}