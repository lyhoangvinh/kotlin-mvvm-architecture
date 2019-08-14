package com.lyhoangvinh.simple.data.source.avg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.base.service.BasePageKeyedDataSource
import com.lyhoangvinh.simple.data.source.base.service.BaseRxPageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class VideoDataSource @Inject constructor(private val avgleService: AvgleService) :
    BaseRxPageKeyedDataSource<Video, VideosResponseAvgle>() {

    private var chId: String = ""

    fun setChId(chId: String) {
        this.chId = chId
    }

    override fun getResourceFollowable(page: Int): Flowable<Resource<BaseResponseAvgle<VideosResponseAvgle>>> =
        createResource(avgleService.getVideosFromKeyword(page, chId), null)

    @Singleton
    class VideoFactory @Inject constructor(private val avgleService: AvgleService) :
        DataSource.Factory<Int, Video>() {

        private val sourceLiveData = MutableLiveData<VideoDataSource>()
        private var newSource: VideoDataSource? = VideoDataSource(avgleService)
        private var chId: String? = ""

        fun setChId(chId: String) {
            this.chId = chId
        }

        fun stateLiveSource(): MutableLiveData<State> {
            return newSource?.getStateLiveData()!!
        }

        fun dispose() {
            newSource?.dispose()
        }

        fun invalidate() {
            sourceLiveData.value?.invalidate()
        }

        override fun create(): DataSource<Int, Video> {
            newSource = null
            newSource = VideoDataSource(avgleService)
            newSource!!.setChId(chId.toString())
            sourceLiveData.postValue(newSource)
            return newSource!!
        }
    }
}