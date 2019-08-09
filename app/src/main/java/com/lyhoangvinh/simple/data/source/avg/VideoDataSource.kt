package com.lyhoangvinh.simple.data.source.avg

import androidx.paging.DataSource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.service.BasePageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class VideoDataSource @Inject constructor(private val avgleService: AvgleService) :
    BasePageKeyedDataSource<Video, VideosResponseAvgle>() {

    var chId: String = ""

    override fun getRequest(page: Int): Single<BaseResponseAvgle<VideosResponseAvgle>> =
        avgleService.getVideosFromKeyword(page, chId)

    @Singleton
    class VideoFactory @Inject constructor(private val provider: Provider<VideoDataSource>) :
        DataSource.Factory<Int, Video>() {

        fun setChId(chId: String) {
            this.provider.get().chId = chId
        }

        fun stateLiveSource() = provider.get().stateLiveData

        fun setSateLiveSource(mCompositeDisposable: CompositeDisposable) {
            provider.get().compositeDisposable = mCompositeDisposable
        }

        fun clear() {
            provider.get().clear()
        }

        fun invalidate() {
            provider.get().invalidate()
        }

        override fun create(): DataSource<Int, Video> {
            return provider.get()
        }
    }

}