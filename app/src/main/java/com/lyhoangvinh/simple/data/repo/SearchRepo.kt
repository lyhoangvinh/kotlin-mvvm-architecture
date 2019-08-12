package com.lyhoangvinh.simple.data.repo

import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.ResponseBiZip
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import io.reactivex.Flowable
import javax.inject.Inject

class SearchRepo @Inject constructor(private val avgleService: AvgleService) : BaseRepo(){

    fun search(
        isRefresh: Boolean,
        query: String,
        page: Int
    ): Flowable<Resource<ResponseBiZip<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>>> {
        return createResource(isRefresh, avgleService.searchVideos(query, page), avgleService.searchJav(query, page),
            object :
                OnSaveBiResultListener<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>> {
                override fun onSave(
                    data: ResponseBiZip<BaseResponseAvgle<VideosResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>,
                    isRefresh: Boolean
                ) {

                    val data1 = data.t1
                    val data2 = data.t1
                    val newList: ArrayList<Video> = ArrayList()
                    if (data1 != null && data1.success && data1.response.videos.isNotEmpty()) {
                        newList.addAll(data1.response.videos)
                    }
                    if (data2 != null && data2.success && data2.response.videos.isNotEmpty()) {
                        newList.addAll(data2.response.videos)
                    }

                    for (x in 0 until newList.size) {
                        newList[x].type = Constants.TYPE_SEARCH
                    }
//                    if (isRefresh) {
//                        backgroundThreadExecutor.runOnBackground {
//                            videosDao.deleteType(Constants.TYPE_SEARCH)
//                            videosDao.insertIgnore(newList)
//                            videosDao.updateIgnore(newList)
//                        }
//                    } else {
//                        backgroundThreadExecutor.runOnBackground {
//                            videosDao.insertIgnore(newList)
//                            videosDao.updateIgnore(newList)
//                        }
//                    }

                }
            })
    }
}