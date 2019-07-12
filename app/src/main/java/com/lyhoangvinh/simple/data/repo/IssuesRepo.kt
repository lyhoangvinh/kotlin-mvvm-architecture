package com.lyhoangvinh.simple.data.repo


import android.arch.paging.LivePagedListBuilder
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.Resource
import io.reactivex.Flowable
import javax.inject.Inject

class IssuesRepo @Inject constructor(private val comicVineService: ComicVineService, private val issuesDao: IssuesDao) :
    BaseRepo() {

    fun liveData() = LivePagedListBuilder(issuesDao.getAllPaged(), 10).build()

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