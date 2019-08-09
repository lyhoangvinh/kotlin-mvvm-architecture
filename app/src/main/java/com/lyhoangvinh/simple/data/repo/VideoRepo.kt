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

class VideoRepo @Inject constructor(private val videoFactory: VideoDataSource.VideoFactory) : BaseRepo() {

    private lateinit var live: LiveData<PagedList<Video>>

    private var chId: String = ""
    private lateinit var mCompositeDisposable: CompositeDisposable

    fun setUpRepo(chId: String, mCompositeDisposable: CompositeDisposable) {
        this.chId = chId
        this.mCompositeDisposable = mCompositeDisposable
        videoFactory.setChId(chId)
        videoFactory.setSateLiveSource(mCompositeDisposable)
    }

    private fun liveVideo(): LiveData<PagedList<Video>> {
        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(100)
            .setPrefetchDistance(50)
            .build()
        live = LivePagedListBuilder(videoFactory, config).build()
        return live
    }

    fun stateVideoSource() = videoFactory.stateLiveSource()

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

    fun reSet() {
//        CoroutineScope(Dispatchers.Default + Job()).launch {
//            videoFactory.liveDataSource.value?.invalidate()
//        }
        execute {
            videoFactory.invalidate()
        }
    }
}