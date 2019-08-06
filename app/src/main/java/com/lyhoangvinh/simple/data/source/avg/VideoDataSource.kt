package com.lyhoangvinh.simple.data.source.avg

import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.service.BasePageKeyedDataSource
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class VideoDataSource @Inject constructor(private val avgleService: AvgleService) :
    BasePageKeyedDataSource<Video, VideosResponseAvgle>(),
    Provider<BasePageKeyedDataSource<Video, VideosResponseAvgle>> {

    var chId = ""

    override fun getRequest(page: Int): Single<BaseResponseAvgle<VideosResponseAvgle>> =
        avgleService.getVideosFromKeyword(page, chId)

    override fun get(): BasePageKeyedDataSource<Video, VideosResponseAvgle> {
        return this
    }

    @Singleton
    class VideoFactory @Inject constructor(private val provider: VideoDataSource) :
        Factory<Video, VideosResponseAvgle>(provider) {
        fun setChId(chId: String) {
            provider.chId = chId
        }
    }

}