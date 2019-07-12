package com.lyhoangvinh.simple.ui.features.test

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.repo.IssuesRepo
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.source.State
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val issuesRepo: IssuesRepo) :
    BaseListDataViewModel<MainAdapter>() {

    override fun canLoadMore() = false

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        fetchData()
        issuesRepo.liveData().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
            hideNoDataState(it.isNullOrEmpty())
        })
    }

    override fun fetchData() {
        execute(true, issuesRepo.getRepoIssues(isRefreshed, 0), null)
    }

    fun clearData() = issuesRepo.deleteAll()
}