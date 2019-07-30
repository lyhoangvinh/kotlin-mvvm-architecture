package com.lyhoangvinh.simple.data.repo

import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.service.BaseItemKeyedDataSource
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ComicPagingDataSource @Inject constructor(private val comicVineService: ComicVineService) :
    BaseItemKeyedDataSource<Issues>(), Provider<BaseItemKeyedDataSource<Issues>> {

    override fun getRequest(): Single<BaseResponseComic<Issues>> =
        comicVineService.getIssues(20, pageNumber, Constants.KEY, "json", "cover_date: desc")

    @Singleton
    class ComicPagingFactory @Inject constructor(provider: ComicPagingDataSource) : Factory<Issues>(provider)

    override fun get(): BaseItemKeyedDataSource<Issues> {
        return this
    }

}