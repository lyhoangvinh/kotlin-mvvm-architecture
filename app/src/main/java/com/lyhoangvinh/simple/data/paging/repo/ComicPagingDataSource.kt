package com.lyhoangvinh.simple.data.paging.repo

import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.paging.source.BaseItemKeyedDataComicSource
import com.lyhoangvinh.simple.data.paging.source.State
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider

class ComicPagingDataSource @Inject constructor(private val comicVineService: ComicVineService) :
    BaseItemKeyedDataComicSource<Issues>(), Provider<BaseItemKeyedDataComicSource<Issues>> {

    override fun getRequest(): Single<BaseResponseComic<Issues>> =
        comicVineService.getIssues(20, pageNumber, Constants.KEY, "json", "cover_date: desc")

    class ComicPagingFactory @Inject constructor(private var provider: ComicPagingDataSource) :
        Factory<Issues>(provider) {

        fun setStateLiveData(stateLiveData: SafeMutableLiveData<State>){
            this.provider.stateLiveData = stateLiveData
        }

        fun clear() {
            provider.clear()
        }
    }

    override fun get(): BaseItemKeyedDataComicSource<Issues> {
        return this
    }

}