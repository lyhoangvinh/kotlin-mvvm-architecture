package com.lyhoangvinh.simple.data.source.comic

import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.base.service.BasePageKeyedDataComicSource
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ComicPagingDataSource @Inject constructor(private val comicVineService: ComicVineService) :
    BasePageKeyedDataComicSource<Issues>(), Provider<BasePageKeyedDataComicSource<Issues>> {

    override fun getRequest(page: Int): Single<BaseResponseComic<Issues>> =
        comicVineService.getIssues(20, page, Constants.KEY, "json", "cover_date: desc")

    @Singleton
    class ComicPagingFactory @Inject constructor(provider: ComicPagingDataSource) : Factory<Issues>(provider)

    override fun get(): BasePageKeyedDataComicSource<Issues> {
        return this
    }

}