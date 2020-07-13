package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.source.avg.SearchDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface SearchPagedRepo {
    fun rxFetchData(compositeDisposable: CompositeDisposable): MediatorLiveData<MergedData>
    fun setQuery(query: String)
}

