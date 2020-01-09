package com.lyhoangvinh.simple.data.source.avg

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.base.service.BaseRxPageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.newPlainConsumer
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoDataSource @Inject constructor(private val avgleService: AvgleService) :
    BaseRxPageKeyedDataSource<Video, VideosResponseAvgle>() {

    private var chId: String = ""

    fun setChId(chId: String) {
        this.chId = chId
    }

    override fun getResourceFollowable(page: Int): Flowable<Resource<BaseResponseAvgle<VideosResponseAvgle>>> =
        createResource(avgleService.getVideosFromKeyword(page, chId), newPlainConsumer {

        })

    @Singleton
    class VideoFactory @Inject constructor(private val avgleService: AvgleService) :
        DataSource.Factory<Int, Video>() {

        private val sourceLiveData = MutableLiveData<VideoDataSource>()
        private var chId: String? = ""
        private var stateLiveData: SafeMutableLiveData<State>? = null
        private var compositeDisposable: CompositeDisposable? = null

        fun setChId(chId: String) {
            this.chId = chId
        }

        fun setStateLiveData(stateLiveData: SafeMutableLiveData<State>) {
            this.stateLiveData = stateLiveData
        }

        fun setCompositeDisposable(compositeDisposable: CompositeDisposable) {
            this.compositeDisposable = compositeDisposable
        }

        fun invalidate() {
            sourceLiveData.value?.invalidate()
        }

        override fun create(): DataSource<Int, Video> {
            val newChId = chId.toString()
            return VideoDataSource(avgleService).apply {
                setChId(newChId)
                setCompositeDisposable(compositeDisposable)
                setStateLiveData(stateLiveData)
                sourceLiveData.postValue(this)
            }
        }
    }
}