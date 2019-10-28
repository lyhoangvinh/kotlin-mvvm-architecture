package com.lyhoangvinh.simple.data.source.avg

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.data.services.AvgleService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.base.service.BaseRxPageKeyedDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

class SearchDataSource @Inject constructor(private val avgleService: AvgleService) :
    BaseRxPageKeyedDataSource<Video, VideosResponseAvgle>() {

    private var query = ""

    fun setQuery(query: String) {
        this.query = query
    }

    override fun getResourceFollowable(page: Int): Flowable<Resource<BaseResponseAvgle<VideosResponseAvgle>>> =
        createResource(avgleService.searchVideos(query, page), null)

    @Singleton
    class SearchFactory @Inject constructor(private val avgleService: AvgleService) :
        DataSource.Factory<Int, Video>() {

        private val sourceLiveData = MutableLiveData<SearchDataSource>()
        private var query: String? = ""
        private var compositeDisposable: CompositeDisposable? = null
        private var stateLiveData: SafeMutableLiveData<State>? = null
        private var emptyLiveData: SafeMutableLiveData<DataEmpty>? = null

        fun setQuery(query: String) {
            this.query = query
        }

        fun setStateLiveData(stateLiveData: SafeMutableLiveData<State>) {
            this.stateLiveData = stateLiveData
        }

        fun setEmptyLiveData(emptyLiveData: SafeMutableLiveData<DataEmpty>) {
            this.emptyLiveData = emptyLiveData
        }

        fun setCompositeDisposable(compositeDisposable: CompositeDisposable) {
            this.compositeDisposable = compositeDisposable
        }

        fun invalidate() {
            sourceLiveData.value?.invalidate()
        }

        override fun create(): DataSource<Int, Video> {
            val newQuery = query.toString()
            return SearchDataSource(avgleService).apply {
                setQuery(newQuery)
                setUpPageKeyedDataSource(stateLiveData, emptyLiveData, compositeDisposable)
                sourceLiveData.postValue(this)
            }
        }
    }
}