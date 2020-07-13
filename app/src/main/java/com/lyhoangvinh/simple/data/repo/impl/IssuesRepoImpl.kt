package com.lyhoangvinh.simple.data.repo.impl

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.repo.BaseRepo
import com.lyhoangvinh.simple.data.repo.IssuesRepo
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.source.comic.ComicLocalPagingDataSource
import com.lyhoangvinh.simple.data.source.comic.ComicPagingDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class IssuesRepoImpl @Inject constructor(
    private val comicVineService: ComicVineService,
    private val issuesDao: IssuesDao,
    private val comicPagingFactory: ComicPagingDataSource.ComicPagingFactory,
    private val comicLocalPagingFactory: ComicLocalPagingDataSource.ComicLocalPagingFactory
) : IssuesRepo, BaseRepo() {

    override fun liveData(): LiveData<PagedList<Issues>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(10)
            .build()
        return LivePagedListBuilder(issuesDao.getAllPaged(), config).build()
    }

    override fun livePagingData(
        stateLiveData: SafeMutableLiveData<State>,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<Issues>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(10)
            .build()
        comicPagingFactory.setUpProvider(stateLiveData, compositeDisposable)
        return LivePagedListBuilder(comicPagingFactory, config).build()
    }

    override fun clear() {
//        comicPagingFactory.clear()
        comicLocalPagingFactory.clear()
    }

    override fun invalidate() {
//        comicPagingFactory.invalidate()
        comicLocalPagingFactory.reset()
    }

    override fun liveLocalPagingData(
        stateLiveData: SafeMutableLiveData<State>,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<Issues>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(40)
            .setPrefetchDistance(10)
            .build()
        comicLocalPagingFactory.setUpProvider(stateLiveData, compositeDisposable)
        return LivePagedListBuilder(comicLocalPagingFactory, config).build()
    }

    override fun delete(t: Issues) = issuesDao.delete(t)

    override fun insert(t: Issues) = issuesDao.insert(t)

    override fun getRepoIssues(refresh: Boolean, offset: Int): Flowable<Resource<BaseResponseComic<Issues>>> {
        return createResource(refresh, comicVineService.getIssues(
            20, offset, Constants.KEY,
            "json",
            "cover_date: desc"
        ), onSave = object : OnSaveResultListener<BaseResponseComic<Issues>> {
            override fun onSave(data: BaseResponseComic<Issues>, isRefresh: Boolean) {
                if (data.results.isNotEmpty()) {
                    execute {
                        if (isRefresh) {
                            issuesDao.removeAll()
                        }
                        issuesDao.insertIgnore(data.results)
                        issuesDao.updateIgnore(data.results)
                    }
                }
            }
        })
    }

    override fun deleteAll() {
        execute {
            issuesDao.removeAll()
        }
    }
}