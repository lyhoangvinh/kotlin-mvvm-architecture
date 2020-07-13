package com.lyhoangvinh.simple.data.repo.impl

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.MergedData
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.entities.avgle.VideoData
import com.lyhoangvinh.simple.data.repo.BaseRepo
import com.lyhoangvinh.simple.data.repo.VideoRepo
import com.lyhoangvinh.simple.data.source.avg.VideoDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(private val videoFactory: VideoDataSource.VideoFactory) : VideoRepo,
    BaseRepo() {

    override fun setUpRepo(chId: String) {
        execute {
            videoFactory.setChId(chId)
            videoFactory.invalidate()
        }
    }

    override fun fetchData(compositeDisposable: CompositeDisposable): MediatorLiveData<MergedData> {
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