package com.lyhoangvinh.simple.ui.features.comic.testpaging

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.paging.repo.IssuesRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicAdapter
import javax.inject.Inject

class ComicPagingViewModel @Inject constructor(private val issuesRepo: IssuesRepo) :
    BaseListDataViewModel<ComicAdapter>() {

    override fun fetchData(page: Int) {

    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        issuesRepo.livePagingData().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        issuesRepo.clear()
    }
}