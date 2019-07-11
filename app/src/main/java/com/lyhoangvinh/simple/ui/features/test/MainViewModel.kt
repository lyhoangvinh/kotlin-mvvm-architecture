package com.lyhoangvinh.simple.ui.features.test

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.repo.IssuesRepo
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val issuesRepo: IssuesRepo) :
    BaseListDataViewModel<Issues, MainAdapter>() {

    override fun canLoadMore() = false

    override fun onFirsTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        issuesRepo.liveData().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun fetchData() {
        execute(false, issuesRepo.getRepoIssues(true, 0), null)
    }
}