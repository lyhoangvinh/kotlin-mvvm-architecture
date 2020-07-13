package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.source.avg.VideoDataSource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface VideoRepo  {
    fun setUpRepo(chId: String)
    fun fetchData(compositeDisposable: CompositeDisposable): MediatorLiveData<MergedData>
}

