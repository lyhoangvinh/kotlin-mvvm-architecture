package com.lyhoangvinh.simple.data.paging.repo


import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.paging.source.Resource
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import io.reactivex.Flowable
import javax.inject.Inject

class IssuesRepo @Inject constructor(
    private val comicVineService: ComicVineService,
    private val issuesDao: IssuesDao,
    private val comicPagingDataSource: ComicPagingDataSource.ComicPagingFactory
) :
    BaseRepo() {

    fun liveData() = LivePagedListBuilder(issuesDao.getAllPaged(), 10).build()

    fun livePagingData(): LiveData<PagedList<Issues>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(40)
            .build()
        return LivePagedListBuilder(comicPagingDataSource, config).build()
    }

    fun clear() {
        comicPagingDataSource.clear()
    }

    fun delete(t: Issues) = issuesDao.delete(t)

    fun insert(t: Issues) = issuesDao.insert(t)

    fun getRepoIssues(refresh: Boolean, offset: Int): Flowable<Resource<BaseResponseComic<Issues>>> {
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

    fun deleteAll() {
        execute {
            issuesDao.removeAll()
        }
    }
}