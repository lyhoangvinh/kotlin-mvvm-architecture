package com.lyhoangvinh.simple.data.source.comic

import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.base.local.BaseLocalPageKeyedDataSource
import com.lyhoangvinh.simple.utils.ConnectionLiveData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ComicLocalPagingDataSource @Inject constructor(
    private val comicVineService: ComicVineService,
    private val issuesDao: IssuesDao,
    connectionLiveData: ConnectionLiveData
) :
    BaseLocalPageKeyedDataSource<Issues>(connectionLiveData), Provider<BaseLocalPageKeyedDataSource<Issues>> {

    override fun getResult(): List<Issues> = issuesDao.getAll()

    override fun saveResultListener(isRefresh: Boolean, data: List<Issues>) {
        if (isRefresh) {
            issuesDao.removeAll()
        }
        issuesDao.insertIgnore(data)
        issuesDao.updateIgnore(data)
    }

    override fun getRequest(page: Int): Single<BaseResponseComic<Issues>> =
        comicVineService.getIssues(20, page, Constants.KEY, "json", "cover_date: desc")

    @Singleton
    class ComicLocalPagingFactory @Inject constructor(provider: ComicLocalPagingDataSource) : Factory<Issues>(provider)

    override fun get(): BaseLocalPageKeyedDataSource<Issues> {
        return this
    }

}