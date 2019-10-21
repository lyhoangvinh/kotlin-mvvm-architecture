package com.lyhoangvinh.simple.ui.features.comic.testactivity

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import android.os.Bundle
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.repo.IssuesRepo
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import javax.inject.Inject

class ComicViewModel @Inject constructor(private val issuesRepo: IssuesRepo) :
    BaseListDataViewModel<ComicAdapter>() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        refresh()
        issuesRepo.liveData().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
//            hideNoDataState(it.isNullOrEmpty())
        })
    }

    override fun fetchData(page: Int) {
        execute(true, issuesRepo.getRepoIssues(isRefreshed, page), object : PlainConsumer<BaseResponseComic<Issues>> {
            override fun accept(t: BaseResponseComic<Issues>) {
                isRefreshed = false
                canLoadMore = t.results.isNotEmpty()
            }
        })
    }

    fun clearData() {
        issuesRepo.deleteAll()
        canLoadMore = false
    }
}