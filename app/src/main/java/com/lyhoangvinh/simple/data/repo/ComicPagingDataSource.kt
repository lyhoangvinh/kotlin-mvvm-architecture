package com.lyhoangvinh.simple.data.repo

import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.service.BasePageKeyedDataSource
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ComicPagingDataSource @Inject constructor(private val comicVineService: ComicVineService) :
    BasePageKeyedDataSource<Issues>(), Provider<BasePageKeyedDataSource<Issues>> {

    override fun getRequest(page: Int): Single<BaseResponseComic<Issues>> =
        comicVineService.getIssues(20, page, Constants.KEY, "json", "cover_date: desc")

    @Singleton
    class ComicPagingFactory @Inject constructor(provider: ComicPagingDataSource) : Factory<Issues>(provider)

    override fun get(): BasePageKeyedDataSource<Issues> {
        return this
    }

}