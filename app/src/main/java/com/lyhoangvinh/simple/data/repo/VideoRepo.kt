package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.source.avg.VideoDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VideoRepo @Inject constructor(private val videoFactory: VideoDataSource.VideoFactory) :
    BaseRepo() {

    private var chId: String = ""

    fun setUpRepo(chId: String) {
        this.chId = chId
        execute {
            videoFactory.setChId(chId)
            videoFactory.invalidate()
        }
    }

    fun fetchData(compositeDisposable: CompositeDisposable): MediatorLiveData<MergedData> {
        val stateLiveData = SafeMutableLiveData<State>()
        return MediatorLiveData<MergedData>().apply {
            addSource(LivePagedListBuilder(videoFactory.apply {
                setCompositeDisposable(compositeDisposable)
                setStateLiveData(stateLiveData)
            }, PagedList.Config.Builder().apply {
                setPageSize(50)
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(100)
                    .setPrefetchDistance(50)
            }.build()).build()) {
                value = VideoData(it)
            }
            addSource(stateLiveData) {
                value = StateData(it)
            }
        }
    }
}