package com.lyhoangvinh.simple.data.repo


import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.source.comic.ComicLocalPagingDataSource
import com.lyhoangvinh.simple.data.source.comic.ComicPagingDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface IssuesRepo {
    fun invalidate()
    fun clear()
    fun deleteAll()
    fun insert(t: Issues)
    fun delete(t: Issues)
    fun liveData(): LiveData<PagedList<Issues>>
    fun getRepoIssues(refresh: Boolean, offset: Int): Flowable<Resource<BaseResponseComic<Issues>>>
    fun livePagingData(stateLiveData: SafeMutableLiveData<State>, compositeDisposable: CompositeDisposable): LiveData<PagedList<Issues>>
    fun liveLocalPagingData(stateLiveData: SafeMutableLiveData<State>, compositeDisposable: CompositeDisposable): LiveData<PagedList<Issues>>
}

